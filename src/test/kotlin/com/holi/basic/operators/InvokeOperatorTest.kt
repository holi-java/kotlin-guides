package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class InvokeOperatorTest {

    @Test
    fun invoke0() {
        val get: () -> String = { "foo" };

        assert.that(get(), equalTo(get.invoke()));
        assert.that(get(), equalTo("foo"));
    }

    @Test
    fun invoke1() {
        val square: (Int) -> Int = { it * it };

        assert.that(square(3), equalTo(square.invoke(3)));
        assert.that(square(3), equalTo(9));
    }

    @Test
    fun invokeN() {
        fun sum(vararg numbers: Int): Int {
            return numbers.sum();
        }

        assert.that(sum(1, 2, 3), equalTo(::sum.invoke(intArrayOf(1, 2, 3))));
        assert.that(sum(1, 2, 3), equalTo(6));
    }

}