package com.holi.annotations

import com.natpryce.hamkrest.*
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses

class AnnotationsTest {
    @Retention
    annotation class Fancy;

    @com.holi.annotations.AnnotationsTest.Fancy
    fun foo() = null;

    @org.junit.Test
    fun `annotation default retention is runtime`() {
        com.natpryce.hamkrest.assertion.assert.that(this::foo.annotations, com.natpryce.hamkrest.anyElement(com.natpryce.hamkrest.isA<Fancy>()));
    }

    class Constructor @com.holi.annotations.AnnotationsTest.Fancy constructor() {/**/ }

    @org.junit.Test
    fun `annotated on constructor`() {
        com.natpryce.hamkrest.assertion.assert.that(com.holi.annotations.AnnotationsTest::Constructor.annotations, com.natpryce.hamkrest.anyElement(com.natpryce.hamkrest.isA<Fancy>()));
    }

    @org.junit.Test
    fun `annotated on property accessor`() {
        class PropertyAccess {
            var value = 1
                @com.holi.annotations.AnnotationsTest.Fancy set (value) {
                    field = value;
                };
        }

        com.natpryce.hamkrest.assertion.assert.that(PropertyAccess::value.setter.annotations, com.natpryce.hamkrest.anyElement(com.natpryce.hamkrest.isA<Fancy>()));
    }


    annotation class Variable(val type: kotlin.reflect.KClass<*> = Any::class);

    @com.holi.annotations.AnnotationsTest.Variable
    fun default() = 1;

    @com.holi.annotations.AnnotationsTest.Variable(Int::class)
    fun special() = 1;


    @org.junit.Test
    fun `annotation with constructors for java 'Class' parameters by kotlin 'KClass'`() {
        com.natpryce.hamkrest.assertion.assert.that(this::default.findAnnotation<com.holi.annotations.AnnotationsTest.Variable>()!!.type, com.natpryce.hamkrest.equalTo<KClass<*>>(Any::class));
        com.natpryce.hamkrest.assertion.assert.that(this::special.findAnnotation<com.holi.annotations.AnnotationsTest.Variable>()!!.type, com.natpryce.hamkrest.equalTo<KClass<*>>(Int::class));
    }


    annotation class Variables(val values: Array<com.holi.annotations.AnnotationsTest.Variable>);

    @com.holi.annotations.AnnotationsTest.Variables(values = arrayOf(com.holi.annotations.AnnotationsTest.Variable(), com.holi.annotations.AnnotationsTest.Variable(String::class)))
    fun variables() = 2;

    @org.junit.Test
    fun `an annotation is used as a parameter of another annotation, its name is not prefixed with the @ character`() {
        val it = this::variables.findAnnotation<com.holi.annotations.AnnotationsTest.Variables>()!!;

        com.natpryce.hamkrest.assertion.assert.that(it.values.map { it.type }, com.natpryce.hamkrest.equalTo(listOf(Any::class, String::class)));
    }


    @org.junit.Test
    fun `any type annotated with kotlin Metadata`() {
        com.natpryce.hamkrest.assertion.assert.that(javaClass.annotations.map { it::class.superclasses.map { it.qualifiedName!! } }.flatten(), com.natpryce.hamkrest.anyElement(com.natpryce.hamkrest.equalTo("kotlin.Metadata")));
    }

    @Repeatable
    @Retention(kotlin.annotation.AnnotationRetention.SOURCE)//todo: exercise it with RUNTIME retention in the next kotlin version
    annotation class Parameter(val name: String);

    @com.holi.annotations.AnnotationsTest.Parameter("foo")
    @com.holi.annotations.AnnotationsTest.Parameter("bar")
    fun repeat() = 3;

    @org.junit.Test
    fun `repeatable annotations can only using for kotlin source file`() {
        com.natpryce.hamkrest.assertion.assert.that(this::repeat.annotations.size, com.natpryce.hamkrest.equalTo(0));
    }

    @[org.junit.Test com.holi.annotations.AnnotationsTest.Variable]
    fun `annotated with multi annotations inline`() {
        val it = this::`annotated with multi annotations inline`.annotations;

        com.natpryce.hamkrest.assertion.assert.that(it, com.natpryce.hamkrest.anyElement(com.natpryce.hamkrest.isA<Test>()) and com.natpryce.hamkrest.anyElement(com.natpryce.hamkrest.isA<Variable>()));
    }


    @com.holi.oop.stubs.ArrayAttribute("foo", "bar")
    fun fn() = 1;

    @org.junit.Test
    fun `java annotation array attribute is treat as vararg`() {
        com.natpryce.hamkrest.assertion.assert.that(this::fn.findAnnotation<com.holi.oop.stubs.ArrayAttribute>()!!.value.toList(), com.natpryce.hamkrest.equalTo(listOf("foo", "bar")));
    }

    annotation class Many(val first: String, val mid: String = "key", val last: String = "fuzz")

    @com.holi.annotations.AnnotationsTest.Many("foo")
    fun required() = 3;

    @org.junit.Test
    fun `use the required parameter for the default parameter in kotlin, unlike java only the 'value' attribute does`() {
        com.natpryce.hamkrest.assertion.assert.that(this::required.findAnnotation<com.holi.annotations.AnnotationsTest.Many>()!!, com.natpryce.hamkrest.has(Many::first, equalTo("foo")) and com.natpryce.hamkrest.has(Many::last, equalTo("fuzz")));
    }
}