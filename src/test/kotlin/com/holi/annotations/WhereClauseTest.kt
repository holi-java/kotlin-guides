package com.holi.annotations

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.io.Closeable
import java.io.Serializable
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.starProjectedType


class WhereClauseTest {
    fun <T> accept(value: T) where T : Serializable, T : Closeable = Unit;

    @Test
    fun types() {
        val it = this::class.declaredMemberFunctions.find { it.name == "accept" }!!;
        val actual = it.typeParameters[0];

        assert.that(actual.upperBounds, equalTo<Any>(listOf(Serializable::class.starProjectedType, Closeable::class.starProjectedType)));
    }
}


