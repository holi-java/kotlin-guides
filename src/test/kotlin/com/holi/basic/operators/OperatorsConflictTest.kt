package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class OperatorsConflictTest {

    @Test
    fun `+`() {
        val it = Quantity(1);

        val added = it + 2;

        assert.that(it, equalTo(Quantity(1)));
        assert.that(added, equalTo(Quantity(3)));
    }


    @Test
    fun `+=`() {
        val origin = Quantity(1);
        val it = origin;

        it += "2";

        assert.that(it, equalTo(Quantity(3)));
        assert.that(origin, equalTo(Quantity(3)));
    }

    data class Quantity(internal var value: Int);

    operator fun Quantity.plus(addend: Int) = copy(this.value + addend);
    operator fun Quantity.plusAssign(addend: String) {
        this.value += addend.toInt();
    };

}

