package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class BitwiseOperatorsTest {
    val it = -2;

    @Test
    fun and() {
        assert.that(it and 1, equalTo(0));
        assert.that(it and 2, equalTo(2));
    }

    @Test
    fun or() {
        assert.that(it or 1, equalTo(-1));
        assert.that(it or 2, equalTo(-2));
    }

    @Test
    fun xor() {
        assert.that(it xor 2, equalTo(0xFFFFFFFC.toInt()));
    }

    @Test
    fun inv() {
        assert.that(it.inv(), equalTo(1));
    }

    @Test
    fun `shift left`() {
        val actual = it shl 1;

        assert.that(actual, equalTo(it.shl(1)));
        assert.that(it, equalTo(-2));
        assert.that(actual, equalTo(-4));
    }

    @Test
    fun `shift right`() {
        val actual = it shr 1;

        assert.that(actual, equalTo(it.shr(1)));
        assert.that(it, equalTo(-2));
        assert.that(actual, equalTo(-1));
    }

    @Test
    fun `unsigned shift right`() {
        val actual = it ushr 1;

        assert.that(actual, equalTo(it.ushr(1)));
        assert.that(it, equalTo(-2));
        assert.that(actual, equalTo(0x7FFFFFFF));
    }

}