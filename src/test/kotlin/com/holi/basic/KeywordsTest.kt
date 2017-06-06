package com.holi.basic

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.sameInstance
import com.natpryce.hamkrest.throws
import org.junit.Test

class KeywordsTest {
    @Test
    fun cast() {
        val value: List<Int> = ArrayList();

        assert.that(value as ArrayList, sameInstance(value));
        assert.that({ value as Set<*> }, throws(isA<ClassCastException>()));
    }

    @Test
    fun `type checking`() {
        val value: Any = ArrayList<Int>();

        assert.that(value is List<*>, equalTo(true));
        assert.that(value is Set<*>, equalTo(false));
    }
}


