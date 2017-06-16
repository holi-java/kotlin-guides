@file: Suppress("WARNINGS")

package com.holi.oop

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class SealedClassTest {
    @Test
    fun `sealed class like as an enum class but its subclass have diff states and behaviors`() {
        assert.that(Constant.map { value }, equalTo(1));
        assert.that(Variable("foo").map { name }, equalTo("foo"));
    }

    object DynamicVariable : Variable("bar");

    @Test
    fun `subclass of sealed class can be extended`() {
        assert.that(DynamicVariable.map { name }, equalTo("bar"));
    }
}

sealed class Expression;

fun <T : Expression, R> T.map(block: T.() -> R?): R? = this.block();

object Constant : Expression() {
    val value = 1;
};
open class Variable(val name: String) : Expression();