package com.holi.basic.operators

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import org.junit.Test
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class LogicOperatorsTest {
    @Test
    fun and() {
        assert.that(TRUE && TRUE, equalTo(TRUE.and(TRUE)));
        assert.that(TRUE && TRUE, equalTo(TRUE));
        assert.that(TRUE && FALSE, equalTo(FALSE));
        assert.that(FALSE && TRUE, equalTo(FALSE));
        assert.that(FALSE && FALSE, equalTo(FALSE));
    }

    @Test
    fun or() {
        assert.that(TRUE || TRUE, equalTo(TRUE.or(TRUE)));
        assert.that(TRUE || TRUE, equalTo(TRUE));
        assert.that(TRUE || FALSE, equalTo(TRUE));
        assert.that(FALSE || TRUE, equalTo(TRUE));
        assert.that(FALSE || FALSE, equalTo(FALSE));
    }

    @Test
    fun xor() {
        assert.that(TRUE xor TRUE, equalTo(TRUE.xor(TRUE)));
        assert.that(TRUE xor TRUE, equalTo(FALSE));
        assert.that(TRUE xor FALSE, equalTo(TRUE));
        assert.that(FALSE xor  TRUE, equalTo(TRUE));
        assert.that(FALSE xor FALSE, equalTo(FALSE));
    }

    @Test
    fun not() {
        assert.that(!TRUE, equalTo(TRUE.not()));
        assert.that(!TRUE, equalTo(FALSE));
        assert.that(!FALSE, equalTo(TRUE));
    }

    @Test
    fun `or shorting-circuited`() {
        assert.that(TRUE || throw IllegalStateException(), equalTo(TRUE));
    }

    @Test
    fun `and shorting-circuited`() {
        assert.that(FALSE && throw IllegalStateException(), equalTo(FALSE));
    }
}
