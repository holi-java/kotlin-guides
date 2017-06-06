package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ComparisionOperatorsTest {
    val it = 1;

    @Test
    fun `less than`() {
        assert.that(it < 2, equalTo(it.compareTo(2) < 0));
    }

    @Test
    fun `greater than`() {
        assert.that(it > 0, equalTo(it.compareTo(0) > 0));
    }

    @Test
    fun `less than or equal`() {
        assert.that(it <= 1, equalTo(it.compareTo(1) <= 0));
    }

    @Test
    fun `greater than or equal`() {
        assert.that(it >= 1, equalTo(it.compareTo(1) >= 0));
    }
}