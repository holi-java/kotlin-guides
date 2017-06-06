package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class BinaryOperatorsTest {

    @Test
    fun plus() {
        val it = listOf(1, 2);
        val result = it + 3

        assert.that(it, equalTo(listOf(1, 2)));
        assert.that(result, equalTo(it.plus(3)));
    }

    @Test
    fun `plus assign`() {
        val it = arrayListOf(1, 2);
        val expected = arrayListOf(1, 2);

        it += 3;
        expected.plusAssign(3);

        assert.that(it, equalTo(expected));
        assert.that(it, equalTo(arrayListOf(1, 2, 3)));
    }

    @Test
    fun minus() {
        val it = listOf(1, 2, 1);
        val result = it - 1;

        assert.that(result, equalTo(it.minus(1)));
        assert.that(it, equalTo(listOf(1, 2, 1)));
        assert.that(result, equalTo(listOf(2, 1)));
        assert.that(result, !equalTo(listOf(1, 2)));
    }

    @Test
    fun `minus assign`() {
        val it = arrayListOf(1, 2, 1);
        val expected = arrayListOf(1, 2, 1);

        it -= 1;
        expected.minusAssign(1);

        assert.that(it, equalTo(arrayListOf(2, 1)));
        assert.that(it, equalTo(expected));
    }

    @Test
    fun times() {
        val it = 2;
        val actual = it * 3;

        assert.that(it, equalTo(2));
        assert.that(actual, equalTo(6));
        assert.that(actual, equalTo(it.times(3)));
    }

    @Test
    fun `times assign`() {
        var it = 2;
        it *= 3;

        assert.that(it, equalTo(2.times(3)));
        assert.that(it, equalTo(6));
    }

    @Test
    fun div() {
        val it = 6;
        val actual = it / 2;

        assert.that(actual, equalTo(it.div(2)));
        assert.that(it, equalTo(6));
        assert.that(actual, equalTo(3));
    }

    @Test
    fun mod() {
        val it = 6;
        val actual = it % 5;

        assert.that(actual, equalTo(it.mod(5)));
        assert.that(it, equalTo(6));
        assert.that(actual, equalTo(1));
    }

    @Test
    fun rem() {
        var it = 6;
        it %= 5;

        assert.that(it, equalTo(it.rem(5)));
        assert.that(it, equalTo(1));
    }

    @Test
    fun `range to`() {
        val it = 1..3;

        assert.that(it, equalTo(1.rangeTo(3)));
        assert.that(it.toList(), equalTo(listOf(1, 2, 3)));
    }
}