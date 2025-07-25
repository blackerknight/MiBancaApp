package com.example.mibancaapp.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object KeystoreHelper {
    private const val KEY_ALIAS = "room_db_key"
    private const val PREFS_NAME = "secure_prefs"
    private const val ENCRYPTED_PASSPHRASE_KEY = "encrypted_passphrase"
    private const val IV_KEY = "encryption_iv"

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

        val existingKey = keyStore.getKey(KEY_ALIAS, null) as? SecretKey
        if (existingKey != null) {
            return existingKey
        }

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    fun getDatabasePassphrase(context: Context): ByteArray {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val encryptedBase64 = prefs.getString(ENCRYPTED_PASSPHRASE_KEY, null)
        val ivBase64 = prefs.getString(IV_KEY, null)

        val secretKey = getOrCreateSecretKey()

        return if (encryptedBase64 != null && ivBase64 != null) {
            val encrypted = Base64.decode(encryptedBase64, Base64.DEFAULT)
            val iv = Base64.decode(ivBase64, Base64.DEFAULT)
            decryptData(encrypted, secretKey, iv)
        } else {
            val passphrase = ByteArray(32).apply { SecureRandom().nextBytes(this) }
            val (encrypted, iv) = encryptData(passphrase, secretKey)
            prefs.edit()
                .putString(ENCRYPTED_PASSPHRASE_KEY, Base64.encodeToString(encrypted, Base64.DEFAULT))
                .putString(IV_KEY, Base64.encodeToString(iv, Base64.DEFAULT))
                .apply()
            passphrase
        }
    }

    private fun encryptData(data: ByteArray, secretKey: SecretKey): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(data)
        return Pair(encrypted, iv)
    }

    private fun decryptData(data: ByteArray, secretKey: SecretKey, iv: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return cipher.doFinal(data)
    }
}
