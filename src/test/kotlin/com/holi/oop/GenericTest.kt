package com.holi.oop

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.Assertion
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test


class GenericTest {
    @Test
    fun `makes generic type arguments explicitly`() {
        val it: Pair<Number, String> = Pair<Number, String>(1, "foo");

        assert.that(it.first, equalTo<Number>(1));
        assert.that(it.second, equalTo("foo"));
    }

    @Test
    fun `generic type parameters may be inferred`() {
        val it: Pair<Number, String> = Pair(1, "foo");

        assert.that(it.first, equalTo<Number>(1));
        assert.that(it.second, equalTo("foo"));
    }

    interface Source<out T> {
        fun next(): T;
    }

    @Test
    fun `generic type parameters can be up to derived types by using declaration-site variance `() {
        val origin: Source<String> = object : Source<String> {
            override fun next() = "foo";
        };

        val up: Source<Any> = origin;

        assert.that(up.next(), equalTo<Any>("foo"));
    }

    interface Consumer<in T> {
        fun accept(value: T);
    }

    @Test
    fun `declaration-site contravariant`() {
        val received = ArrayList<Number>();
        val source: Consumer<Number> = object : Consumer<Number> {
            override fun accept(value: Number) {
                // todo: how to replace with method reference expression?
                received.add(value);
            }
        };

        val it: Consumer<Double> = source;

        it.accept(1.0);
        it.accept(2.0);

        assert.that(received, equalTo(listOf<Number>(1.0, 2.0)));
    }

    @Test
    fun `out type projections`() {
        val source: Array<Int> = arrayOf(1);

        val it: Array<out Any> = source;

        assert.that(it.toList(), equalTo(listOf<Any>(1)));
        assert.that({ (source as Array<Any>)[0] = "foo" }, throws(isA<ArrayStoreException>()));
    }

    @Test
    fun `in type projections`() {
        val source: Array<Number> = arrayOf(1);

        val it: Array<in Int> = source;

        it[0] = 2;
        assert.that(it.toList(), equalTo(listOf<Any?>(2)));
    }

    @Test
    fun `star type projections like as out type projection 'out Any?'`() {
        val source: Array<Number> = arrayOf(1);

        val it: Array<*> = source;

        assert.that(it.toList(), equalTo(listOf<Any?>(1)));
    }

    @Test
    fun `star type projections like as in type projection 'in Nothing'`() {
        val source: Consumer<*> = object : Consumer<Any?> {
            override fun accept(value: Any?) {
            }
        };

        val it: Consumer<Nothing> = source;

        assert.that(it, sameInstance(source));
    }


    @Test
    fun `up-bound type parameters constraints`() {
        fun <T : CharSequence> T.first() = this[0];

        assert.that("abc".first(), equalTo('a'));
        assert.that(StringBuilder("abc").first(), equalTo('a'));

    }
}

