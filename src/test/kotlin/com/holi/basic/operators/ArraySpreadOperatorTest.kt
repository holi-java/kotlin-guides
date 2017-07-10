package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.*
import java.util.Arrays.asList

class ArraySpreadOperatorTest {

    @Test
    fun `spread an array to varargs`() {
        val args = intArrayOf(2, 3, 4);

        fun sum(vararg items: Int) = items.sum();

        assert.that(sum(*args), equalTo(9));
    }

    @Test
    fun `spread arrays into another array`() {
        val array = arrayOf(1, *arrayOf(2, 3), *arrayOf(*arrayOf(4)), 5)

        assert.that(array.toList(), equalTo(listOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun `spread array contains null`() {
        val strings = asList("foo", *arrayOf<String?>(null), "bar")

        assert.that(strings, equalTo(listOf("foo", null, "bar")))
    }
}