package com.holi.oop

import cancel
import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.lang.Integer.parseInt
import java.util.concurrent.CancellationException

/**
 * Created by holi on 6/6/17.
 */
class FunctionTest {

    @Test
    fun `function is implemented to a class too`() {
        fun foo() = 2;

        assert.that(::foo, isA<Function0<*>>());
    }

    @Test
    fun `single-expression function is able to infer its return type automatically`() {
        fun sum(a: Int, b: Int) = a + b;

        assert.that(sum(1, 2), equalTo(3));
    }

    @Test
    fun `anonymous function's parameter types can be omitted`() {
        val it: (Int) -> String = fun(value) = value.toString();

        assert.that(it(0), equalTo("0"));
    }

    @Test
    fun `uses Unit to makes function doesn't return any value`() {
        var received: String? = null;

        fun accept(value: String) {
            received = value;
        }

        accept("foo");

        assert.that(received, equalTo("foo"));
    }


    @Test
    fun `ref a bounded method reference expression`() {
        val repeat: (Int) -> String = "foo"::repeat;

        assert.that(repeat(2), equalTo("foofoo"));
    }

    @Test
    fun `ref an unbounded method reference expression`() {
        val repeat: (String, Int) -> String = String::repeat;

        assert.that(repeat("foo", 2), equalTo("foofoo"));
    }

    @Test
    fun `default parameters`() {
        fun times(it: Int, times: Int = 2): Int {
            return it * times;
        }

        assert.that(times(3), equalTo(6));
        assert.that(times(3, 3), equalTo(9));
    }

    @Test
    fun `pass named arguments`() {
        fun div(a: Int, b: Int) = a / b;

        assert.that(div(b = 2, a = 6), equalTo(3));
    }


    @Test
    fun `using named arguments to solve 'vararg' is not declared at last parameter on the function`() {
        fun sum(start: Int, vararg numbers: Int, step: Int) = (start until numbers.size step step).map(numbers::get).sum();

        assert.that(sum(1, 2, 3, 4, step = 2), equalTo(3));
    }

    @Test
    fun `'vararg' is declared at last parameter on the function`() {
        fun sum(start: Int, step: Int, vararg numbers: Int) = (start until numbers.size step step).map(numbers::get).sum();

        assert.that(sum(1, 1, 2, 3, 4), equalTo(7));
    }

    @Test
    fun `function be able to omit parentheses when the last parameter is a function`() {
        fun triple(provider: () -> Int) = 3 * provider();

        assert.that(triple { 2 }, equalTo(6));
    }

    @Test
    fun `function can be interrupted with a function that return nothing`() {
        fun sum(error: () -> Nothing): Any {
            error.invoke();
        }

        assert.that({ sum { cancel(); } }, throws(isA<CancellationException>()));
    }


    @Test
    fun `uses infix function to write dsl code`() {
        infix fun String.toInt(radix: Int): Int = parseInt(this, radix);

        assert.that("F" toInt 16, equalTo(15));
    }

    @Test
    fun `conditional expression`() {
        fun type(it: Int) = if (it % 2 == 0) "even" else "odd";

        assert.that(type(1), equalTo("odd"));
        assert.that(type(2), equalTo("even"));
    }

    @Test
    fun `when expression`() {
        fun mean(it: Int) = when (it) {
            1 -> "one";
            2 -> "two";
            else -> "unknown";
        };

        assert.that(mean(1), equalTo("one"));
        assert.that(mean(2), equalTo("two"));
        assert.that(mean(3), equalTo("unknown"));
    }

    @Test
    fun `function supports tail-recursion`() {
        tailrec fun Int.closeTo(it: Int): Int = if (this + 2 > it) this else (this + 1).closeTo(it);

        assert.that(3.closeTo(7), equalTo(6));
    }

    @Test
    fun `calls with a special receiver object 'this'`() {
        val negate = fun Int.() = -this;

        assert.that(1.negate(), equalTo(-1));
    }

}