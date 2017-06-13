package com.holi.oop

import com.holi.basic.next
import com.natpryce.hamkrest.anyElement
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.Test
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberExtensionFunctions

class ExtensionTest {

    @Test
    fun `extension methods`() {
        fun String.isBlank() = this.matches("\\s*".toRegex());

        assert.that("", has(String::isBlank, equalTo(true)));
        assert.that("\n", has(String::isBlank, equalTo(true)));
        assert.that(" b a ", has(String::isBlank, equalTo(false)));
    }

    @Test
    fun `extension property`() {
        val it = object {
            val <T> Array<T>.length: Int get () = this.size;

            fun <T> length(array: Array<T>) = array.length;
        }

        assert.that(it.length(arrayOf("foo", "bar")), equalTo(2));
    }

    @Test
    fun `extension operators`() {
        operator fun String.get(start: Int, end: Int): String {
            return this.substring(start until end);
        }

        assert.that("foo"[0, 2], equalTo("fo"))
    }


    @Test
    fun `declare an extension method as variable`() {
        val times = fun String.(times: Int) = this.repeat(times);

        assert.that("ab".times(3), equalTo("ababab"));
    }

    class C {
        fun foo() = "member";
    }

    @Test
    fun `extension priority is lower than member`() {
        fun C.foo() = "extension";

        val it = C();

        assert.that(it.foo(), equalTo("member"));
    }

    class Foo {
        companion object;
    };

    @Test
    fun `extension companion objects`() {
        fun Foo.Companion.foo() = "bar";

        assert.that(Foo.foo(), equalTo("bar"));
    }


    @Test
    fun `extension function can be overloaded`() {
        fun <T> Array<T>.first(offset: Int) = this[offset];
        fun <T> Array<T>.first() = first(0);

        val it = arrayOf(1, 2);

        assert.that(it.first(), equalTo(1));
        assert.that(it.first(1), equalTo(2));
    }

    @Test
    fun `ability to access enclosing members if declare extension as member`() {
        class Owner(val value: Int) {
            fun compose(value: Int) = value.composed();
            fun Int.composed() = this@Owner.value + this;
        }

        val it = Owner(3);

        assert.that(it.compose(2), equalTo(5));
    }

    @Test
    fun `this inside an extension function is ref to receiver object`() {
        fun <T> T.receiver(): T {
            return this;
        }

        assert.that("foo".receiver(), equalTo("foo"));
        assert.that(null.receiver(), equalTo(null));
    }

    @Test
    fun `extension only available in its scope`() {
        val called = (scope@ {
            fun Int.double() = this * 2;

            assert.that(3.double(), equalTo(6));
            //todo: how to check extension is added into class
            return@scope true;
        })();

        assert.that(called, equalTo(true));
        assert.that(Int::class.memberExtensionFunctions, !anyElement(has(KFunction<*>::name, equalTo("double"))));
    }

    @Test
    fun `extension can't be override as further if is defined before`() {
        fun Int.step() = this + 1;

        assert.that(1.step(), equalTo(2));

//        fun Int.step() = this *3;
//
//        assert.that(1.step(), equalTo(3));
    }


    @Test
    fun `auto-imports custom extensions in the same package hierarchy`() {
        assert.that(2.triple(), equalTo(6));
    }


    @Test
    fun `imports custom extensions manually in the diff package hierarchy`() {
        assert.that(2.next(), equalTo(3));
    }

}
