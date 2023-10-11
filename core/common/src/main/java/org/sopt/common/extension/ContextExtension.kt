package org.sopt.common.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.stringOf(id: Int): String = getString(id)

fun Context.stringOf(id: Int, vararg formatArgs: Any): String = getString(id, *formatArgs)