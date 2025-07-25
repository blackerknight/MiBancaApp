package com.example.mibancaapp

import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText

class Utils {
}

fun EditText.setAlphanumericLimit(maxLength: Int) {
    this.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
    this.filters = arrayOf(InputFilter.LengthFilter(maxLength))

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if (!it.toString().matches(Regex("^[a-zA-Z0-9]*$"))) {
                    val filtered = it.toString().replace(Regex("[^a-zA-Z0-9]"), "")
                    this@setAlphanumericLimit.setText(filtered)
                    this@setAlphanumericLimit.setSelection(filtered.length)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun EditText.setEmailInput() {
    this.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_CLASS_TEXT

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            s?.let {
                if (!isValidEmail(it.toString())) {
                    this@setEmailInput.error = "Ingrese un correo válido"
                } else {
                    this@setEmailInput.error = null
                }
            }
        }
    })
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex(
        "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@" +
                "[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"
    )
    return email.matches(emailRegex)
}