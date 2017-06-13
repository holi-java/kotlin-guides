package com.holi.oop

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import org.junit.Test
import kotlin.reflect.KClass

/**
 * Created by holi on 6/6/17.
 */
class ClassReferenceTest {

    @Test
    fun `get class from type`() {
        val type = Runnable::class;

        assert.that(type, isA<KClass<*>>());
        assert.that(type, !isA<Class<*>>());
        assert.that(type.java, isA<Class<*>>());
    }

    @Test
    fun `get class from objects`() {
        val obj = Runnable {};

        assert.that(obj, isA<Runnable>())
        assert.that(obj.javaClass, !isA<KClass<*>>());
        assert.that(obj.javaClass, isA<Class<Runnable>>());
    }
}