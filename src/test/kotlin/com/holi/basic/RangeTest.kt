package com.holi.basic

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

/**
 * Created by holi on 6/6/17.
 */
class RangeTest {

    @Test
    fun `inclusive end`() {
        val range = 1..3;

        assert.that(range.toList(), equalTo(listOf(1, 2, 3)));
    }

    @Test
    fun `exclusive end`() {
        val range = 1 until 3;

        assert.that(range.toList(), equalTo(listOf(1, 2)));
    }

    @Test
    fun `down to`() {
        val range = 3 downTo 1;

        assert.that(range.toList(), equalTo(listOf(3, 2, 1)));
    }

    @Test
    fun `step`() {
        val range = 1..10 step 2;

        assert.that(range.toList(), equalTo(listOf(1, 3, 5, 7, 9)));
    }
}