package org.sopt.common.random

import kotlin.random.Random

fun generateRandomCode(): String {
    val random = Random
    return (1..4)
        .map {
            if (random.nextBoolean()) {
                // Generate a digit (ASCII 48 to 57)
                (random.nextInt(10) + 48).toChar()
            } else {
                // Generate an uppercase letter (ASCII 65 to 90)
                (random.nextInt(26) + 65).toChar()
            }
        }
        .joinToString("")
}

fun generatePaperUserName() = "유령친구${generateRandomCode()}"