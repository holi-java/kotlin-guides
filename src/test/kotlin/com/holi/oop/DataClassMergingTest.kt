package com.holi.oop

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor


class DataClassMergingTest {

    data class Bean(val name: String?, val age: Int?, val id: String?);


    @Test
    fun merge() {
        val data = Bean("foo", null, null);

        val merged = data merge Bean("bar", 2, "3");

        assert.that(merged, equalTo(Bean("foo", 2, "3")));
    }

}

inline infix fun <reified T : Any> T.merge(mapping: KProperty1<T, *>.() -> Any?): T {

    val target = T::class;
    //data class always has primary constructor
    val cotr = target.primaryConstructor!!;


    val parameters = cotr.parameters.associateBy { it.name };

    // merge properties
    val merged: Map<KParameter, Any?> = target.declaredMemberProperties.associate { parameters[it.name]!! to it.mapping() }


    return cotr.callBy(merged);
}

inline infix fun <reified T : Any> T.merge(right: T): T {
    return this merge { get(this@merge) ?: get(right); };
}
