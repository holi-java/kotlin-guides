package com.holi.basic.control_flow

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import org.junit.Test

class IfExpressionTest {


    @Test
    fun `return if expression`() {
        fun mean(it: Int): String {
            return if (it == 1) {
                "one";
            } else if (it == 2) {
                "two"
            } else {
                "more";
            };
        }

        assert.that(mean(1), equalTo("one"));
        assert.that(mean(2), equalTo("two"));
        assert.that(mean(3), equalTo("more"));
    }


    @Test
    fun `if return statement`() {
        fun mean(it: Int): String {
            if (it == 1) {
                return "one";
            } else if (it == 2) {
                return "two";
            } else {
                return "more";
            };
        }

        assert.that(mean(1), equalTo("one"));
        assert.that(mean(2), equalTo("two"));
        assert.that(mean(3), equalTo("more"));
    }
}