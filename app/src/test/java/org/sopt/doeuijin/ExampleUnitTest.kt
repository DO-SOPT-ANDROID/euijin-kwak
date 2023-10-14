package org.sopt.doeuijin

import org.junit.Test

import org.junit.Assert.*
import org.sopt.common.extension.isValidLength

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testIsValidLength() {
        assertTrue("123456".isValidLength(6, 10))
        assertFalse("12345".isValidLength(6, 10))
        assertTrue("1234567890".isValidLength(6, 10))
        assertFalse("12345678901".isValidLength(6, 10))
    }
}