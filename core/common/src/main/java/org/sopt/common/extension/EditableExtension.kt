package org.sopt.common.extension

import android.text.Editable

fun Editable.isNotValidWith(value: String?): Boolean = this.toString() != value
