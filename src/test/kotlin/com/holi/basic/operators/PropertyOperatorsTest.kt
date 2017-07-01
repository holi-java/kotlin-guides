package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class PropertyOperatorsTest {
    @Test
    fun get() {
        val it = object {
            val value: Int  by this;
            operator fun <R> getValue(thisRef: R, property: KProperty<*>) = 1;
        }

        assert.that(it.value, equalTo(1));
    }

    @Test
    fun set() {
        var ref: Int? = null;
        val it = object {
            var value: Int  by this;
            operator fun <R> getValue(thisRef: R, property: KProperty<*>) = ref ?: 0
            operator fun <R> setValue(thisRef: R, property: KProperty<*>, value: Int) {
                ref = value;
            }
        };

        assert.that(it.value, equalTo(0));

        it.value = 2;
        assert.that(it.value, equalTo(2));
        assert.that(ref, equalTo(2));
    }

    private fun shl(value: Int, bits: Int) = object {
        operator fun <T> provideDelegate(target: T, property: KProperty<*>): ReadOnlyProperty<T, Int> {
            return object : ReadOnlyProperty<T, Int> {
                val identity by lazy { value shl bits };
                override fun getValue(thisRef: T, @Suppress("NAME_SHADOWING") property: KProperty<*>): Int {
                    return identity;
                }
            }
        }
    };

    inner class ProvideDelegate {
        val value by shl(1, 2);
    }

    @Test
    fun `delegate provider`() {
        val it = ProvideDelegate();

        assert.that(it.value, equalTo(4));
        assert.that(it.value, equalTo(4));
    }
}