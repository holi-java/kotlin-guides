@file: Suppress("WARNINGS")
package com.holi.basic

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test

class NullSafelyOperationTest {
    @Test
    fun `safe accessing`() {
        var it: String? = "foo";
        assert.that(it!!.length, equalTo(3));
        assert.that(it?.length, equalTo(3));
        assert.that(it.length, equalTo(3));

        it = null;

        assert.that(it?.length, absent());
        assert.that({ it!!.length }, throws(isA<NullPointerException>()));
    }

    @Test
    fun otherwise() {
        fun foo(s: String?): String {
            return s ?: "bar";
        }

        assert.that(foo("foo"), equalTo("foo"));
        assert.that(foo(null), equalTo("bar"));
    }

    @Test
    fun `otherwise throws an exception`() {
        fun foo(s: String?): String {
            return s ?: throw IllegalArgumentException("bad");
        }

        assert.that(foo("foo"), equalTo("foo"));
        assert.that({ foo(null) }, throws(isA<IllegalArgumentException>(has(Exception::message, equalTo("bad")))));
    }

    @Test
    fun `execute if not null`() {
        fun length(s: String?): Int? {
            return s?.let { it.length }
        }

        assert.that(length("foo"), equalTo(3));
        assert.that(length(null), absent());
    }


}