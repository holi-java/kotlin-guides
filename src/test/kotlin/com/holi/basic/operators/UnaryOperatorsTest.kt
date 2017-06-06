package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class UnaryOperatorsTest {

    @Test
    fun `unary plus`() {
        val it = +-6;

        assert.that(it, equalTo(-6.unaryPlus()));
        assert.that(it, equalTo(-6));
    }

    @Test
    fun `unary minus`() {
        val it = -6;

        assert.that(it, equalTo(6.unaryMinus()));
        assert.that(it, equalTo(-6));
    }

    @Test
    fun inc() {
        var it = -6;
        val actual = it++;

        assert.that(it, equalTo((-6).inc()));
        assert.that(actual, equalTo(-6));
        assert.that(it, equalTo(-5));
    }

    @Test
    fun dec() {
        var it = -6;

        val actual = it--;

        assert.that(it, equalTo((-6).dec()));
        assert.that(actual, equalTo(-6));
        assert.that(it, equalTo(-7));
    }

    @Test
    fun inc2() {
        var it = -6;
        val actual = ++it;

        assert.that(it, equalTo((-6).inc()));
        assert.that(actual, equalTo(-5));
        assert.that(it, equalTo(-5));
    }

    @Test
    fun dec2() {
        var it = -6;

        val actual = --it;

        assert.that(it, equalTo((-6).dec()));
        assert.that(actual, equalTo(-7));
        assert.that(it, equalTo(-7));
    }

}