package com.holi.matchers

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.endsWith
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.or
import org.junit.Test

/**
 * Created by holi on 6/6/17.
 */
class HamkrestTest {
    @Test
    fun simple() {
        assert.that("foo", equalTo("foo"));
    }

    @Test
    fun conjunction() {
        assert.that("foo", equalTo("foo") and endsWith("oo"));
    }

    @Test
    fun `dis-conjunction`() {
        assert.that("foo", equalTo("foo") or equalTo("bar"));
        assert.that("bar", equalTo("foo") or equalTo("bar"));
    }
}