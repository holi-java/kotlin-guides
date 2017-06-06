package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class IndexedAccessOperatorsTest {

    @Test
    fun get() {
        val it = listOf("foo", "bar");

        assert.that(it[0], equalTo(it.get(0)));
        assert.that(it[0], equalTo("foo"));
        assert.that(it[1], equalTo(it.get(1)));
        assert.that(it[1], equalTo("bar"));
    }

    @Test
    fun `get value from key`() {
        val it = mapOf("foo" to 1);

        assert.that(it["foo"], equalTo(it.get("foo")));
        assert.that(it["foo"], equalTo(1));
    }

    @Test
    fun set() {
        val it = mutableMapOf<String, Int>();
        val expected = mutableMapOf<String, Int>()

        it["foo"] = 1;
        expected.set("foo", 1);

        assert.that(it, equalTo(expected));
    }

}