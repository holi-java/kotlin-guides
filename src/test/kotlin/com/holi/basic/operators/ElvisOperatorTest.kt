package com.holi.basic.operators

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test

class ElvisOperatorTest {
    val foo: String? = "bar";
    val nil: String? = null;
    @Test
    fun `default value`() {
        assert.that(accept(foo ?: "baz"), equalTo("bar"));
        assert.that(accept(nil ?: "baz"), equalTo("baz"));
    }

    @Test
    fun `throws exception`() {
        assert.that(accept(foo ?: throw IllegalArgumentException()), equalTo("bar"));
        assert.that({ accept(nil ?: throw IllegalArgumentException()) }, throws(isA<IllegalArgumentException>()));
    }

    @Test
    fun `calling a method return Nothing`() {
        assert.that(accept(foo ?: fail()), equalTo("bar"));
        assert.that({ accept(nil ?: fail()) }, throws(isA<IllegalArgumentException>()));
    }

    @Test
    fun `return the function in advance by 'return' keyword`() {
        assert.that(play(foo), equalTo("Rab"));
        assert.that(play(nil), absent())
    }


    fun play(value: String?): String? {
        val it = value ?: return null;
        return it.reversed().capitalize();
    };

    fun accept(value: String) = value;

    fun fail(): Nothing = throw IllegalArgumentException()

}