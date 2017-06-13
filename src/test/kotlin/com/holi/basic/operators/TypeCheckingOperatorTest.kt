package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

open class TypeCheckingOperatorTest {
    @Test
    fun `type checking`() {
        val value: Any = ArrayList<Int>();

        assert.that(value is List<*>, equalTo(true));
        assert.that(value is Set<*>, equalTo(false));
    }

    @Test
    fun `checking null`() {
        assert.that(null is Nothing, equalTo(false));
        assert.that(null is Unit, equalTo(false));
        assert.that(null is Any, equalTo(false));
    }
}