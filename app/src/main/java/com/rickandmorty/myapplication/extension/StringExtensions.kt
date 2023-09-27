package com.rickandmorty.myapplication.extension

import android.text.TextUtils
import java.util.regex.Pattern


internal fun String.isValidEmail():Boolean = !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

internal fun String.isValidPassword():Boolean = !TextUtils.isEmpty(this) && validatePassword(this)

internal fun String.getUserName():String = this.split("@")[0]

fun validatePassword(password: String): Boolean {

    // check for pattern
    val uppercase: Pattern = Pattern.compile("[A-Z]")
    val lowercase: Pattern = Pattern.compile("[a-z]")
    val digit: Pattern = Pattern.compile("[0-9]")
    return (lowercase.matcher(password).find() && uppercase.matcher(password).find() && digit.matcher(password).find()
            && password.length >=5)
}


