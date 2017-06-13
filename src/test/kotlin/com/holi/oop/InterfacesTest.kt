package com.holi.oop

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test
import java.lang.reflect.Member
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions

class InterfacesTest {
    interface Interface {
        fun foo();
        fun bar() = "bar";
    }

    @Test
    fun `all methods interface can be overridden`() {
        assert.that(Interface::class.functions, allElements(has(KFunction<*>::isOpen, equalTo(true)) or has(KFunction<*>::isAbstract, equalTo(true))));
    }

    @Test
    fun `interface has no constructors`() {
        assert.that(Interface::class, has(KClass<*>::constructors, hasSize(equalTo(0))));
    }

    interface Property1 {
        val value get() = 1;
    }

    @Test
    fun `implements getters in interface`() {
        class Subclass : Property1;

        val it = Subclass();

        assert.that(it.value, equalTo(1));
    }

    interface Property2 {
        var value: Int;
    }

    @Test
    fun `define abstract mutable property in interface will generate getter & setters`() {
        assert.that(Property2::class.java.declaredMethods.map(Member::getName), equalTo(listOf("getValue", "setValue")));
    }

    interface Property3 {
        val value get() = 3;
    }

    @Test
    fun `resolving overriding conflict`() {
        class Subclass : Property1, Property3 {
            override val value get() = super<Property1>.value + super<Property3>.value;
        };

        val it = Subclass();

        assert.that(it.value, equalTo(4));
    }
}