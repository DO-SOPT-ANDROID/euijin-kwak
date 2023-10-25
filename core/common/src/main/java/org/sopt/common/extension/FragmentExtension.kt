package org.sopt.common.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

val Fragment.viewLifeCycle get() = viewLifecycleOwner.lifecycle
val Fragment.viewLifeCycleScope get() = viewLifecycleOwner.lifecycleScope