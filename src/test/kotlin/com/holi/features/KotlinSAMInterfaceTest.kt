package com.holi.features

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class KotlinSAMInterfaceTest {
    @Test
    fun `create kotlin SAM interface like as java`() {
        val mapping = Mapping<Int, Int> { it -> it * 2 };

        assert.that(mapping.map(3), equalTo(6));
    }

}

interface Mapping<in T, out R> {
    fun map(value: T): R;

    companion object {
        inline operator fun <T, R> invoke(crossinline mapping: Function1<T, R>): Mapping<T, R> = object : Mapping<T, R> {
            override fun map(value: T): R = mapping(value);
        };
    }
}