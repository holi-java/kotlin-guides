package com.holi.oop

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import org.junit.Test
import java.util.function.Function

class NestedClassTest {
    class OuterClass(val value: Int) {
        class NestedClass {
            val foo = "bar";
        }

        inner class InnerClass() {
            val refValue get() = value;
            val refThis = this@OuterClass;
        }
    }

    @Test
    fun `class can be nested another class`() {
        val it = OuterClass.NestedClass();

        assert.that(it.foo, equalTo("bar"));
    }

    @Test
    fun `create an inner class instance with instance of outer class`() {
        val root = OuterClass(3)

        val it = root.InnerClass();

        assert.that(it, isA<OuterClass.InnerClass>());
    }

    @Test
    fun `an inner class can access members of outer class`() {
        val it = OuterClass(3).InnerClass();

        assert.that(it.refValue, equalTo(3));
    }

    @Test
    fun `an inner class carry a reference to object of outer class`() {
        val root = OuterClass(3)
        val it = root.InnerClass();

        assert.that(it.refThis, equalTo(root));
    }

    @Test
    fun `using an object expression creates anonymous class instance`() {
        var ran = false;
        val task = object : Runnable {
            override fun run() {
                ran = true;
            }
        };

        task.run();

        assert.that(ran, equalTo(true));
    }

    @Test
    fun `create java SAM interface instance by using a lambda expression prefixed with the type of the interface`() {
        val toInt = Function<String, Int> { it.toInt() };

        assert.that(toInt.apply("1"), equalTo(1));
    }

    @Test
    fun `create java SAM interface instance by using a method reference expression prefixed with the type of the interface`() {
        val toInt = Function(String::toInt);

        assert.that(toInt.apply("1"), equalTo(1));
    }
}