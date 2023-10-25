package org.sopt.common.extension

fun String.isValidLength(minLength: Int, maxLength: Int) = this.length in minLength..maxLength
fun String.isNotValidLength(minLength: Int, maxLength: Int) = this.length !in minLength..maxLength
