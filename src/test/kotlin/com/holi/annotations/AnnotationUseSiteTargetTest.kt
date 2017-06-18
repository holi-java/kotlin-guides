@file:com.holi.oop.stubs.Annotation
@file: Suppress("warnings")

package com.holi.annotations

import com.holi.oop.stubs.Annotation
import com.natpryce.hamkrest.anyElement
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaMethod

class AnnotationUseSiteTargetTest {

    @Test
    fun `can't fetch the @file annotation at runtime`() {
        assert.that(this::class.annotations, !anyElement(isA<Annotation>()));
    }

    @Test
    fun `@property site-target are not visible to java`() {

        class PropertySiteTarget(@property:Annotation val value: Any);

        val it = PropertySiteTarget::value;

        assert.that(it.annotations, isAnnotated);
        assert.that(it.getter.annotations, !isAnnotated);
        assert.that(it.javaField!!.annotations.map { it }, !isAnnotated);
        assert.that(it.getter.javaMethod!!.annotations.map { it }, !isAnnotated);
    }

    @Test
    fun `@field site-target`() {
        class FieldSiteTarget(@field:Annotation val value: Any);


        val it = FieldSiteTarget::value;

        assert.that(it.javaField!!.annotations.toList(), isAnnotated);
        assert.that(it.annotations, !isAnnotated);
        assert.that(it.getter.annotations, !isAnnotated);
    }


    @Test
    fun `@get site-target`() {
        class GetSiteTarget1(@get:Annotation val value: Int = 1);

        val it = GetSiteTarget1::value

        assert.that(it.getter.annotations, !isAnnotated);//todo: it is a kotlin bug?
        assert.that(it.getter.javaMethod!!.annotations.map { it }, isAnnotated);
        assert.that(it.javaField!!.annotations.map { it }, !isAnnotated);
        assert.that(it.annotations, !isAnnotated);
    }

    @Test
    fun `@set site-target`() {
        class SetSiteTarget(@set:Annotation var value: Any = 1);

        val it = SetSiteTarget::value;

        assert.that(it.setter.annotations, !isAnnotated);//todo: it is a kotlin bug?
        assert.that(it.setter.javaMethod!!.annotations.map { it }, isAnnotated);
        assert.that(it.javaField!!.annotations.map { it }, !isAnnotated);
        assert.that(it.annotations, !isAnnotated);
    }

    class ConstructorParamSiteTarget(@param:Annotation val value: Any);

    @Test
    fun `@param site-target`() {
        assert.that(AnnotationUseSiteTargetTest::ConstructorParamSiteTarget.parameters[0].annotations, isAnnotated);
        assert.that(AnnotationUseSiteTargetTest::ConstructorParamSiteTarget.javaConstructor!!.parameters[0].annotations.toList(), isAnnotated);
        assert.that(ConstructorParamSiteTarget::value.run { annotations + getter.annotations }.toList(), !isAnnotated)
    }

    @Test
    fun `@setparam site-target`() {
        class SetParamSiteTarget(@setparam:Annotation var value: Any);

        val it = SetParamSiteTarget::value;

        assert.that(it.setter.parameters[0].annotations, !isAnnotated);//todo: it is a kotlin bug?
        assert.that(it.setter.javaMethod!!.parameters[0].annotations.toList(), isAnnotated);
        assert.that(it.annotations, !isAnnotated);
        assert.that(it.setter.annotations, !isAnnotated);
        assert.that(it.javaField!!.annotations.map { it }, !isAnnotated);
    }

    fun @receiver: Annotation Int.apply(block: Int.() -> Int): Int {
        return block();
    }

    @Test
    fun `@receiver site-target`() {
        val it = this::class.java.declaredMethods.find { it.name == "apply" }!!.parameters[0];

        assert.that(it.type.kotlin, equalTo<KClass<*>>(Int::class));
        assert.that(it.annotations.toList(), isAnnotated);
    }

    @Test
    fun `@delegate site-target`() {
        class DelegatePropertiesSiteTarget {
            @delegate:Annotation val value by lazy { 1 };
        };
        val it = DelegatePropertiesSiteTarget::value.javaField!!;

        assert.that(it.type, equalTo<Class<*>>(Lazy::class.java));
        assert.that(it.annotations.toList(), isAnnotated);
    }

    val isAnnotated get() = anyElement(com.natpryce.hamkrest.isA<Annotation>());
}




