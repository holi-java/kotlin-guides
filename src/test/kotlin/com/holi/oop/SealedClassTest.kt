@file: Suppress("WARNINGS")

package com.holi.oop

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.Test


class SealedClassTest {
    @Test
    fun `sealed class like as an enum class but its subclass have diff states and behaviors`() {
        val constant: Expression = Constant;
        val variable: Expression = Variable();

        assert.that(Constant.value, equalTo(1));
        assert.that(Variable().name, equalTo("foo"));
    }
}

sealed class Expression;
object Constant : Expression() {
    val value = 1;
};
class Variable : Expression() {
    val name = "foo";
}
