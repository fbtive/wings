package com.example.wingsgroup.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.wingsgroup.data.local.UserModel

object WingsAuth {
    var username: String? = null
    var email: String? = null
    var phone: String? = null

    private const val LOGIN_USERNAME = "Auth.Util.Login.Username"
    private const val LOGIN_EMAIL = "Auth.Util.Login.Email"
    private const val LOGIN_PHONE = "Auth.Util.Login.Phone"

    private fun persist(context: Context, username: String, email: String, phone: String) {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(LOGIN_USERNAME, username)
        editor.putString(LOGIN_EMAIL, email)
        editor.putString(LOGIN_PHONE, phone)
        editor.commit()
    }

    fun authenticate(context: Context, user: UserModel) {
        username = user.username
        email = user.email
        phone = user.phone
        persist(context, user.username, user.email, user.phone)
    }

    fun logout(context: Context) {
        username = null
        email = null
        phone = null

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(LOGIN_USERNAME, null)
        editor.putString(LOGIN_EMAIL, null)
        editor.putString(LOGIN_PHONE, null)
        editor.commit()
    }

    fun getLocalData(context: Context) {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        username = preferences.getString(LOGIN_USERNAME, null)
        email = preferences.getString(LOGIN_EMAIL, null)
        phone = preferences.getString(LOGIN_PHONE, null)
    }

}