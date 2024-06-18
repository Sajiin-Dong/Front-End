package com.example.sajiindong.preference

import android.content.Context

class LoginPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    companion object {
        private const val PREFS_NAME = "login_pref"
        private const val NAME = "name"
        private const val USER_ID = "userId"
        private const val TOKEN = "token"
    }
}