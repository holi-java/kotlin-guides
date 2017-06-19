package com.holi.oop

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

@Suppress("unused","UNUSED_PARAMETER")
class ConstructorTest {
    @Test
    fun `parameter`() {
        class Parameter(value: String);

        assert.that(Parameter::class.memberProperties.size, equalTo(0));
        assert.that(Parameter::class.primaryConstructor!!.parameters[0].type, equalTo<Any>(String::class.starProjectedType));
    }

    @Test
    fun property() {
        class Property(val first: String, var second: String);

        assert.that(Property::class.memberProperties.map { it.name }, equalTo(listOf("first", "second")));
    }

    @Test
    fun `assign primary constructor parameter to property`() {
        class Assignment(value: Int) {
            val string = "$value";
        }

        val it = Assignment(1);

        assert.that(it.string, equalTo("1"));
    }
}