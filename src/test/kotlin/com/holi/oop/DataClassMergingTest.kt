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

    //data class always has primary constructor ---v
    val constructor = T::class.primaryConstructor!!
    //calculate the property order
    val order = constructor.parameters.mapIndexed { index, it -> it.name to index }.associate { it };

    // merge properties
    val merged = T::class.declaredMemberProperties.sortedWith(compareBy { order[it.name] }).map { it.mapping() }.toTypedArray()


    return constructor.call(*merged);
}

inline infix fun <reified T : Any> T.merge(right: T): T {
    return this merge { get(this@merge) ?: get(right); };
}
