package com.holi.oop

import com.holi.oop.InlineFunctionsTest.Properties.foo
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class LambdaTest {

    @Test
    fun `variables's type can be inferred automatically`() {
        val get = { "foo" };

        assert.that(get(), equalTo("foo"));
    }

    @Test
    fun `implicit name of a single-parameter 'it'`() {
        val repeat: (Int) -> String = { "foo".repeat(it) };

        assert.that(repeat(2), equalTo("foofoo"));
    }

    @Test
    fun `define parameters explicitly & return type can be inferred automatically`() {
        val repeat = { times: Int -> "foo".repeat(times) };

        assert.that(repeat(2), equalTo("foofoo"));
    }

    val foo = { _: Int -> "bar" };
    @Test
    fun `underscore for unused parameters`() {
        assert.that(foo.invoke(0), equalTo("bar"));
    }

    @Test
    fun `'invoke' operator is overridden`() {
        assert.that(foo(0), equalTo("bar"));
    }

    @Test
    fun `mutable local variables can be changed in lambda`() {
        var i = 0;

        let { i++; }

        assert.that(i, equalTo(1));
    }

    @Test
    fun `calls with a special receiver object 'this'`() {
        val negate: Int.() -> Int = negate@ {
            return@negate -this;
        };

        assert.that(1.negate(), equalTo(-1));
    }

}