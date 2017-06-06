package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class CustomOperatorsTest {

    @Test
    fun get2() {
        val it = Cent(2);

        assert.that(it[0, 3], equalTo(it.get(0, 3)));
        assert.that(it[0, 3], equalTo(listOf(Cent(2), Cent(3))));
    }

    @Test
    fun div() {
        val six = Cent(6);
        val two = Cent(2);

        val actual = six / two;

        assert.that(actual, equalTo(six.div(two)));
        assert.that(actual, equalTo(Cent(3)));
        assert.that(six, equalTo(Cent(6)));
        assert.that(two, equalTo(Cent(2)));
    }

    data class Cent(val value: Int) {
        operator fun div(that: Cent): Cent {
            return Cent(this.value / that.value);
        }

        operator fun get(start: Int, end: Int): List<Cent> {
            return (maxOf(value, start)..maxOf(value, end))
                    .map(::Cent).toList();
        }
    }
}