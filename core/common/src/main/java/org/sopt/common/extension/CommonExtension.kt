package org.sopt.common.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnack(anchorView: View, message: () -> String) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}