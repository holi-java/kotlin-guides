package com.holi.oop

import com.holi.oop.DataClassMergingTest.DTO
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


class DataClassMergingTest {

    data class DTO(val prop1: String, val prop2: Int, val prop3: Int);


    @Test
    fun merge() {
        val data = DTO("foo", 1, 2);

        val merged = merge(data, DTO("bar", 2, 3));

        assert.that(merged, equalTo(DTO("foo", 1, 2)));
    }
}


/**
 *
 * @receiver T
 * @param KProperty1<T,*>.()-Any? mapping
 * @return T
 */
infix fun <T : Any> T.merge(mapping: KProperty1<T, *>.() -> Any?): T {
    @Suppress("UNCHECKED_CAST")
    val merged = (this::class as KClass<T>).memberProperties.map { it.mapping() }.toTypedArray()

    return this::class.primaryConstructor!!.call(*merged);
}


fun merge(left: DTO, right: DTO): DTO {
    return left merge mapping@ {
        //    v--- implement your own merge strategy
        val it = this.get(left)
        val that = this.get(right)
        return@mapping when (it) {
            null -> that
            else -> it
        }
    };
}