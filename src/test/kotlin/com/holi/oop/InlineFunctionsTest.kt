package com.holi.oop

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.junit.Assert.fail
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

class InlineFunctionsTest {
    inline fun apply(task: () -> Unit) {};
    fun run(task: () -> Unit) {};

    @Test
    fun `un-inline function will be generated anonymous class by compiler`() {
        run { };

        assert.that(this::`un-inline function will be generated anonymous class by compiler`.anonymousClass, present());
    }

    @Test
    fun `inline function will be inline to the call-site function`() {
        apply { };

        assert.that(this::`inline function will be inline to the call-site function`.anonymousClass, absent());
    }

    val KFunction<*>.anonymousClass: Class<*>? get() {
        try {
            val anonymousClassName = "%s$%s$1".format(this.javaClass.enclosingClass.name, name)
            return ClassLoader.getSystemClassLoader().loadClass(anonymousClassName);
        } catch(e: ClassNotFoundException) {
            return null;
        }
    }

    inline fun invoke(crossinline block: () -> Unit) {
        val task = object {
            fun invoke() = block();
        };

        task.invoke();
    }

    @Test
    fun `uses cross-inline to ref a function from inline function to another execution context`() {
        var ran = false;

        invoke { ran = true; }

        assert.that(ran, equalTo(true));
    }

    @Test
    fun `inline function is able to return the enclosing function immediately`() {
        val source = listOf(1, 2, 3);
        fun list(): List<Int> {
            val result = arrayListOf<Int>();
            source.forEach {
                result += it;
                return result;
            }

            fail();
            return emptyList();
        }

        assert.that(list(), equalTo(listOf(1)));
    }


    @Test
    fun `return the lambda by return@label but the lambda is not returned immediately`() {
        val source = listOf(1, 2, 3);
        fun list(): List<Int> {
            val result = arrayListOf<Int>();
            source.forEach {
                result += it;
                return@forEach;
            }

            return result;
        }

        assert.that(list(), equalTo(listOf(1, 2, 3)));
    }

    object Reified {
        inline fun <reified T> type(): KClass<*> = T::class;
    }

    @Test
    fun `uses reified to access a type passed to us as a parameter`() {
        assert.that(Reified.type<Int>(), equalTo<KClass<*>>(Int::class));
    }

    object Properties {
        val foo = 1;
        inline val bar get() = 1;
    };

    @Test
    fun `inline properties will doesn't generate a backing field`() {
        val it = Properties::class.declaredMemberProperties

        assert.that(it, hasSize(equalTo(2)));
        assert.that(it.find { it.name == "foo" }!!, has(KProperty1<*, *>::javaField, present()));
        assert.that(it.find { it.name == "bar" }!!, has(KProperty1<*, *>::javaField, absent()));
    }
}