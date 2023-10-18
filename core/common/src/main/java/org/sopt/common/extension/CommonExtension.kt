package org.sopt.common.extension

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun showSnack(anchorView: View, message: () -> String) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun showSnack(anchorView: View, message: String) {
    Snackbar.make(anchorView, message, Snackbar.LENGTH_SHORT).show()
}

fun showSnack(anchorView: View, @StringRes messageRes: Int) {
    Snackbar.make(anchorView, messageRes, Snackbar.LENGTH_SHORT).show()
}