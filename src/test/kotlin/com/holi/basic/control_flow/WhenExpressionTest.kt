@file: Suppress("WARNINGS")

package com.holi.basic.control_flow

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import org.junit.Assert.fail
import org.junit.Test
import java.lang.Integer.parseInt

class WhenExpressionTest {


    @Test
    fun `matching`() {
        fun mean(it: Int): String {
            return when (it) {
                1 -> "one";
                2 -> "two";
                else -> "more";
            };
        }

        assert.that(mean(1), equalTo("one"));
        assert.that(mean(2), equalTo("two"));
        assert.that(mean(3), equalTo("more"));
    }


    @Test
    fun `checking type`() {
        fun typeOf(it: Any): String {
            return when (it) {
                is Number -> "number";
                is String -> "string";
                else -> "<unknown>";
            };
        }

        assert.that(typeOf(1), equalTo("number"));
        assert.that(typeOf("foo"), equalTo("string"));
        assert.that(typeOf(1..2), equalTo("<unknown>"));
    }

    @Test
    fun `matching many cases`() {
        fun fuzz(it: Int): Boolean {
            return when (it) {
                3, 15 -> true;
                else -> false;
            }
        }

        assert.that(fuzz(1), equalTo(false));
        assert.that(fuzz(3), equalTo(true));
        assert.that(fuzz(15), equalTo(true));
    }

    @Test
    fun `matching arbitrary expression`() {
        val s = "2";

        when (2) {
            parseInt(s) -> return;
            else -> fail();
        }
    }

    @Test
    fun `use as replacement for if-else statement`() {
        val it = 2;

        when {
            it % 2 == 0 -> return;
            else -> fail();
        }
    }

    @Test
    fun `matching once`() {
        val it = 1;

        val actual = when (it) {
            is Number -> "number";
            1 -> "odd";
            else -> "unknown";
        }

        assert.that(actual, equalTo("number"));
    }
}