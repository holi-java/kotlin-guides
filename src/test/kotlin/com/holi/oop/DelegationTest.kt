package com.holi.oop

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.util.*
import kotlin.reflect.KCallable
import kotlin.reflect.KFunction0
import kotlin.reflect.KProperty0

class DelegationTest {

    interface Action {
        val foo: String;
        val bar: String;
    }

    val default = object : Action {
        override val foo: String = "foo";
        override val bar: String get() = throw  IllegalStateException();
    };

    @Test
    fun `delegate methods which is not overridden`() {
        lazy { }
        class ActionProxy(target: Action) : Action by target {
            override val bar = "baz";
        }

        val it = ActionProxy(default);

        assert.that(it.foo, equalTo("foo"));
        assert.that(it.bar, equalTo("baz"));
    }


    @Test
    fun `delegate properties to Map`() {
        val map = mapOf<String, Any?>("name" to "bob", "invalid" to "1")
        val it = object {
            val name: String  by map;
            val missing: Int by map;
            val invalid: Int by map;
            val nullable: Int? by map;
        };

        assert.that(it.name, equalTo("bob"));
        assert.that({ it.missing }, throws(isA<NoSuchElementException>()));
        assert.that({ it.nullable }, throws(isA<NoSuchElementException>()));
        assert.that({ it.invalid }, throws(isA<ClassCastException>()));
    }

    @Test
    fun `delegate properties to MutableMap`() {
        val map = mutableMapOf<String, String>("name" to "bob")
        val it = object {
            var name: String  by map;
        };

        it.name = "john";

        assert.that(it.name, equalTo("john"));
        assert.that(map["name"], equalTo(it.name));
    }
}
