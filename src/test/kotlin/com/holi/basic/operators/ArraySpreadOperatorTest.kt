package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ArraySpreadOperatorTest {

    @Test
    fun `spread an array to varargs`() {
        val args = intArrayOf(2, 3, 4);

        fun sum(vararg items: Int) = items.sum();

        assert.that(sum(*args), equalTo(9));
    }
}