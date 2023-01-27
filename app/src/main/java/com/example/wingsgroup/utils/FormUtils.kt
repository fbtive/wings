package com.example.wingsgroup.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun hideKeyboard(context: Context, view: View) {
    val im = ContextCompat.getSystemService(context, InputMethodManager::class.java) as InputMethodManager
    im.hideSoftInputFromWindow(view.windowToken, 0)
}