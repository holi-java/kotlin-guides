package com.holi.oop

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.reflect.KVisibility
import kotlin.reflect.KVisibility.*
import kotlin.reflect.full.functions

class ValueObjectTest {
    data class Dollar(val amount: Int);
    @Test
    fun `data only`() {
        val it = Dollar(5);

        assert.that(it, equalTo(Dollar(5)));
        assert.that(it.amount, equalTo(5));
        assert.that(it.copy(), equalTo(Dollar(5)));
        assert.that(it.copy(3), equalTo(Dollar(3)));
    }

    data class Money(val amount: Int, val currency: String) {

        operator fun times(times: Int): Money {
            return copy(amount = amount * times);
        }
    }

    @Test
    fun `adding additional behavior`() {


        val it = Money(5, "CHF");

        assert.that(it * 3, equalTo(Money(15, "CHF")));
        assert.that(it, equalTo(Money(5, "CHF")));
    }

    @Test
    fun `generates component(N) functions corresponding to the properties in their order of declaration`() {
        val components = Money::class.functions.map { it.name }
                .filter { it.matches(Regex("component\\d+")) }
                .toList();
        assert.that(components, equalTo(listOf("component1", "component2")));

        val (amount, currency) = Money(3, "USD");

        assert.that(amount, equalTo(3));
        assert.that(currency, equalTo("USD"));
    }

    data class Visibility(private val first: Int, protected val second: Int, val last: Int);

    @Test
    fun `component(N)'s visibility as the property`() {
        val it = Visibility::class.functions
                .filter { it.name.startsWith("component") }
                .sortedBy { it.name }
                .map { it.visibility };

        assert.that(it, equalTo(listOf<KVisibility?>(PRIVATE, PROTECTED, PUBLIC)));
    }

}

