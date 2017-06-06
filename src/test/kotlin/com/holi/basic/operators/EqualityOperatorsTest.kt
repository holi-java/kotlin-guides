package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class EqualityOperatorsTest {
    val it: Int = 1;

    @Test
    fun equal() {
        assert.that(it == 1, equalTo(it.equals(1)));
    }

    @Test
    fun `not equal`() {
        assert.that(it != 2, equalTo(!it.equals(2)));
    }

}