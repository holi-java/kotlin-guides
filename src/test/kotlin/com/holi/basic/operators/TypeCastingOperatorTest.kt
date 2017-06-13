package com.holi.basic.operators

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.sameInstance
import com.natpryce.hamkrest.throws
import org.junit.Test

class TypeCastingOperatorTest {
    @Test
    fun `unsafe type casting`() {
        val it: List<Int> = ArrayList();

        assert.that(it as ArrayList, sameInstance(it));
        assert.that({ it as Set<*> }, throws(isA<ClassCastException>()));
    }

    @Test
    fun `safe type casting`() {
        val it: List<Int> = ArrayList();

        assert.that(it as? MutableList, sameInstance<Any?>(it));
        assert.that(it as? Set<*>, absent());
    }
}


