@file: Suppress("WARNINGS")
package com.holi.features

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.coroutines.experimental.jvm.internal.CoroutineImpl
import kotlin.jvm.functions.Function1

@Suppress("EXPERIMENTAL")
class CoroutinesTest {

    @Test(timeout = 100)
    fun `don't blocking the main thread`() {
        produce<Unit> { Thread.sleep(2000); }
    }

    @Test
    fun `starts coroutines in the same thread`() {
        val it = produce { yield(Thread.currentThread()); };

        assert.that(it.get(), equalTo(Thread.currentThread()));
    }

    @Test
    fun `suspend function types`() {
        val type = suspend {};

        assert.that(type.superclass, equalTo<Class<*>>(CoroutineImpl::class.java));
        assert.that(type.interfaces.toList(), equalTo(listOf<Class<*>>(Function1::class.java)));
    }

    private fun suspend(function: suspend () -> Unit) = function.javaClass;

    var steps = ArrayBlockingQueue<Int>(1);

    fun <T> ArrayBlockingQueue<T>.await() = this.poll(100, MILLISECONDS);
    @Test
    fun `producer & consumer pattern`() {
        val it = produce {
            steps.add(1);
            yield("foo");
            steps.add(2);
            yield("bar");
            steps.add(3);
        };

        assert.that(steps.await(), absent());

        assert.that(it.get(), equalTo("foo"));
        assert.that(steps.await(), equalTo(1));

        assert.that(it.get(), equalTo("bar"));
        assert.that(steps.await(), equalTo(2));

        assert.that({ it.get() }, throws(isA<NoSuchElementException>()));
        assert.that(steps.await(), equalTo(3));
    }

    @Test
    fun `consumer receives exception from producer`() {
        val it = produce<Unit> { throw IllegalAccessException(); };

        assert.that({ it.get() }, throws(isA<IllegalAccessException>()))
    }

    @Test
    fun `producer & consumer async pattern`() {
        val it = produce(CommonPool) {
            steps.add(1);
            yield("foo");
            steps.add(2);
            yield("bar");
            steps.add(3);
        };

        assert.that(steps.await(), equalTo(1));
        assert.that(it.get(), equalTo("foo"));
        assert.that(steps.await(), absent());

        assert.that(it.get(), equalTo("bar"));
        assert.that(steps.await(), equalTo(2));

        assert.that({ it.get() }, throws(isA<NoSuchElementException>()));
        assert.that(steps.await(), equalTo(3));
    }

}



