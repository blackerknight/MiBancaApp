package com.example.mibancaapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mibancaapp.security.KeystoreHelper
import net.sqlcipher.database.SupportFactory
import net.sqlcipher.database.SQLiteDatabase

@Database(
    entities = [CardEntity::class, MovementEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun movementDao(): MovementDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {

                // Todo: Use a strong key and get it from a server
                //val passphrase: ByteArray = SQLiteDatabase.getBytes("myStrongPassphrase@123#".toCharArray())

                val passphrase = KeystoreHelper.getDatabasePassphrase(context)
                val factory = SupportFactory(passphrase)

                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cards_db.db"
                )
                    .openHelperFactory(factory)
                    .fallbackToDestructiveMigration() // Todo: implement a migration
                    .build()
                    .also { INSTANCE = it }
            }
    }
}

