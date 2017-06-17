package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class TernaryOperatorTest {

    @Test
    fun satisfied() {
        val value = true then "foo" ?: "bar";

        assert.that(value, equalTo("foo"));
    }

    @Test
    fun unsatisfied() {
        val value = false then "foo" ?: "bar";

        assert.that(value, equalTo("bar"));
    }

    @Test
    fun `lazy initialized`() {
        val value = true then { "foo" } ?: "bar";

        assert.that(value, equalTo("foo"));
    }

    @Test
    fun `lazy initialized is short-circuiting`() {
        val value = false then { throw Exception() } ?: "bar";

        assert.that(value, equalTo("bar"));
    }
}

@Suppress("NOTHING_TO_INLINE")
inline infix fun <T> Boolean.then(value: T) = this.then { value };

inline infix fun <T> Boolean.then(value: () -> T) = if (this) value() else null;