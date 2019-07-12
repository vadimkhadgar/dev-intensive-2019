@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val context = this
    val inputManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, HIDE_NOT_ALWAYS)
}

