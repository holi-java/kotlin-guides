package com.holi.oop

import com.holi.oop.stubs.ArrayAttribute
import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses

class AnnotationsTest {
    @Retention
    annotation class Fancy;

    @Fancy
    fun foo() = null;

    @Test
    fun `annotation default retention is runtime`() {
        assert.that(this::foo.annotations, anyElement(isA<Fancy>()));
    }

    class Constructor @Fancy constructor() {/**/ }

    @Test
    fun `annotated on constructor`() {
        assert.that(::Constructor.annotations, anyElement(isA<Fancy>()));
    }

    @Test
    fun `annotated on property accessor`() {
        class PropertyAccess {
            var value = 1
                @Fancy set (value) {
                    field = value;
                };
        }

        assert.that(PropertyAccess::value.setter.annotations, anyElement(isA<Fancy>()));
    }


    annotation class Variable(val type: KClass<*> = Any::class);

    @Variable
    fun default() = 1;

    @Variable(Int::class)
    fun special() = 1;


    @Test
    fun `annotation with constructors for java 'Class' parameters by kotlin 'KClass'`() {
        assert.that(this::default.findAnnotation<Variable>()!!.type, equalTo<KClass<*>>(Any::class));
        assert.that(this::special.findAnnotation<Variable>()!!.type, equalTo<KClass<*>>(Int::class));
    }


    annotation class Variables(val values: Array<Variable>);

    @Variables(values = arrayOf(Variable(), Variable(String::class)))
    fun variables() = 2;

    @Test
    fun `an annotation is used as a parameter of another annotation, its name is not prefixed with the @ character`() {
        val it = this::variables.findAnnotation<Variables>()!!;

        assert.that(it.values.map { it.type }, equalTo(listOf(Any::class, String::class)));
    }


    @Test
    fun `any type annotated with kotlin Metadata`() {
        assert.that(javaClass.annotations.map { it::class.superclasses.map { it.qualifiedName!! } }.flatten(), anyElement(equalTo("kotlin.Metadata")));
    }

    @Repeatable
    @Retention(SOURCE)//todo: exercise it with RUNTIME retention in the next kotlin version
    annotation class Parameter(val name: String);

    @Parameter("foo")
    @Parameter("bar")
    fun repeat() = 3;

    @Test
    fun `repeatable annotations can only using for kotlin source file`() {
        assert.that(this::repeat.annotations.size, equalTo(0));
    }

    @[Test Variable]
    fun `annotated with multi annotations inline`() {
        val it = this::`annotated with multi annotations inline`.annotations;

        assert.that(it, anyElement(isA<Test>()) and anyElement(isA<Variable>()));
    }


    @ArrayAttribute("foo", "bar")
    fun fn() = 1;

    @Test
    fun `java annotation array attribute is treat as vararg`() {
        assert.that(this::fn.findAnnotation<ArrayAttribute>()!!.value.toList(), equalTo(listOf("foo", "bar")));
    }

    annotation class Many(val first: String, val mid: String = "key", val last: String = "fuzz")

    @Many("foo")
    fun required() = 3;

    @Test
    fun `use the required parameter for the default parameter in kotlin, unlike java only the 'value' attribute does`() {
        assert.that(this::required.findAnnotation<Many>()!!, has(Many::first, equalTo("foo")) and has(Many::last, equalTo("fuzz")));
    }
}