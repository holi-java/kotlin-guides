package com.holi.basic

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test
import java.util.*

private typealias JavaBoolean = java.lang.Boolean;
class BasicTypesTest {


    @Test
    fun int() {
        val number = 1;

        assert.that(number, isA<Int>());
        assert.that(number, isA<Integer>());
        assert.that(number.javaClass, has(Class<*>::isPrimitive, equalTo(true)));
    }

    @Test
    fun char() {
        val char = 'a';

        assert.that(char, isA<Char>());
        assert.that(char, isA<Character>());
        assert.that(char.javaClass, has(Class<*>::isPrimitive, equalTo(true)))
    }

    @Test
    fun boolean() {
        val bool = true;

        assert.that(bool, isA<Boolean>());
        assert.that(bool, isA<JavaBoolean>());
        assert.that(bool.javaClass, has(Class<*>::isPrimitive, equalTo(true)))
    }

    @Test
    fun `wrapper class`() {
        val value = JavaBoolean(true).javaClass;

        assert.that(value, has(Class<*>::isPrimitive, equalTo(false)));
    }

    @Test
    fun `convert explicitly`() {
        val char = 'a';

        val code = char.toInt();

        assert.that(code, equalTo(97));
    }

    @Test
    fun boxing() {
        val it: Int = 1;
        val boxed: Int? = it;

        assert.that(boxed === it, equalTo(true));
        assert.that(boxed == it, equalTo(true));
    }

    @Test
    fun `initialize an array with nulls`() {
        val it: Array<Int?> = Array(3, { null });

        assert.that(it.toList(), equalTo(arrayOfNulls<Int>(3).toList()));
    }

    @Test
    fun `immutable string`() {
        val raw = "foo";
        var it = raw;

        it += "bar";

        assert.that(it, equalTo("foobar"));
        assert.that(raw, equalTo("foo"));
    }

    @Test
    fun `multi lines string`() {
        val text = """
                        abc
                        efg
                   """;
        assert.that(text.replace(Regex("\\s+"), ""), equalTo("abcefg"));
    }

    @Test
    fun `escape char`() {
        val foo = "bar";

        val it = "\$foo";

        assert.that(it, equalTo("$" + "foo"));
    }

    @Test
    fun `escape literal`() {
        val foo = "bar";

        val it = "${'$'}foo";

        assert.that(it, equalTo("$" + "foo"));
    }
}