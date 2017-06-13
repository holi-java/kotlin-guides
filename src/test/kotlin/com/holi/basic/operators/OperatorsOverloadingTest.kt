package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class OperatorsOverloadingTest {
    data class Dollar(val amount: Int) {
        operator fun plus(amount: Int): Dollar {
            return copy(this.amount + amount);
        }

        operator fun plus(addend: Dollar): Dollar {
            return copy(amount + addend.amount);
        }
    }

    @Test
    fun `operators can be overloaded`() {
        val it = Dollar(1);

        assert.that(it + 2, equalTo(Dollar(3)));
        assert.that(it + Dollar(2), equalTo(Dollar(3)));
    }
}