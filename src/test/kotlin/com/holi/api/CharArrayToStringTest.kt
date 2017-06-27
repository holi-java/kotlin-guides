package com.holi.api

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class CharArrayToStringTest {

    val chars = arrayOf('A', 'B', 'C')

    @Test
    fun `join to string`() {
        val result: String = chars.joinToString("");
        assert.that(result, equalTo("ABC"));
    }

    @Test
    fun `convert to CharArray`() {
        val result: String = String(chars.toCharArray());
        assert.that(result, equalTo("ABC"));
    }

    @Test
    fun `declare primitive char array`() {
        val chars = charArrayOf('A', 'B', 'C');
        val result: String = String(chars);
        assert.that(result, equalTo("ABC"));
    }
}