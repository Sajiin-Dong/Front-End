package com.example.sajiindong.utils

import android.text.TextUtils
import android.util.Patterns
import com.example.sajiindong.constants.Constants

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validateMinLength(password: String): Boolean {
    return !TextUtils.isEmpty(password) && password.length >= Constants.MIN_LENGTH_PASSWORD
}