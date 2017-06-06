package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class InOperatorTest {

    @Test
    fun contains() {
        val it = "foo"
        val map = mapOf(it to 1);

        assert.that(it in map, equalTo(map.contains(it)));
        assert.that(it in map, equalTo(true));
        assert.that("bar" !in map, equalTo(true));
    }

}