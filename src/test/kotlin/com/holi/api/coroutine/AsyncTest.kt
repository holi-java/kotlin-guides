package com.holi.api.coroutine

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.sameInstance
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.io.IOException
import java.util.concurrent.*
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.createCoroutine

class AsyncTest {

    @Test
    fun get() {
        val it = async { "foo" };

        assert.that(it.get(), equalTo("foo"));
    }

    @Test
    fun `result is cached`() {
        val it = async { "foo" };

        assert.that(it.get(), equalTo("foo"));
        assert.that(it.get(), equalTo("foo"));
    }

    @Test
    fun `execute block asynchronous`() {
        val it = async {
            Thread.currentThread();
        };

        assert.that(it.get(), !equalTo(Thread.currentThread()));
    }

    @Test(timeout = 200)
    fun `async don't blocking the main thread`() {
        async {
            Thread.sleep(2000);
        };
    }

    @Test
    fun `async can capture exception from the block`() {
        val expected = IllegalStateException();
        val it = async<Any> {
            throw expected;
        };

        assert.that({ it.get() }, throws(sameInstance(expected)));
    }

    @Test
    fun `restore the exception`() {
        val expected = IllegalStateException();

        val it = async<Any> { throw expected; };

        assert.that({ it.get() }, throws(sameInstance(expected)));
        assert.that({ it.get() }, throws(sameInstance(expected)));
    }


    @Test
    fun `then`() {
        val array = ArrayBlockingQueue<String>(1);

        async { "foo" }.then(array::put);

        assert.that(array.take(), equalTo("foo"))
    }

    @Test
    fun `then clause is applied in another thread`() {
        val array = ArrayBlockingQueue<Thread>(1);

        async { "foo" }.then({ array.add(Thread.currentThread()) });

        assert.that(array.take(), !equalTo(Thread.currentThread()));
    }

    @Test
    fun `then chain`() {
        val it = async { "foo" }.then(String::toUpperCase).then({ it.substring(1) });

        assert.that(it.get(), equalTo("OO"));
    }

    @Test
    fun `catch`() {
        val thrown: Throwable = IllegalStateException();
        val exceptions = ArrayBlockingQueue<Throwable>(1);

        async<Any> { throw thrown; }.catch(exceptions::put);

        assert.that(exceptions.take(), isA<IllegalStateException>());
    }


    @Test
    fun `catch chain`() {
        val exceptions = ArrayBlockingQueue<Throwable>(1);

        async<Any> { throw IOException(); }.catch({ throw IllegalStateException(); }).catch(exceptions::put);

        assert.that(exceptions.take(), isA<IllegalStateException>());
    }

    @Test
    fun `combine result`() {
        val it = async { "foo" };

        assert.that(it.then { it.toUpperCase() }.catch { throw IllegalStateException() }.get(), equalTo("FOO"));
        assert.that(it.catch { throw IllegalStateException() }.then { it.toUpperCase() }.get(), equalTo("FOO"));
    }

    @Test
    fun `combine exception`() {
        val reason = IOException();
        val forward = IllegalStateException();
        val it = async<String> { throw reason };

        assert.that({ it.then { it.toUpperCase() }.catch { throw forward; }.get() }, throws(sameInstance(forward)));
        assert.that({ it.catch { throw forward; }.then { it.toUpperCase() }.get() }, throws(sameInstance(forward)));
    }


}


interface Request<out T> {
    fun get(): T;
    fun <R> then(mapping: (T) -> R): Request<R>;
    fun catch(exceptional: (Throwable) -> Unit): Request<T>
}

private val executor: ExecutorService = ForkJoinPool(20);
fun <T> async(block: suspend () -> T): Request<T> {
    return object : Request<T> {
        @Volatile var value: T? = null;
        var exception: Throwable? = null;
        var step: Continuation<Unit>? = block.createCoroutine(delegate(this::exceptionally, this::complete)).let {
            var task: Future<*>? = executor.submit { it.resume(Unit); };
            return@let delegate {
                try {
                    val current = task!!;
                    task = null;
                    current.get();
                } catch(e: ExecutionException) {
                    throw e.cause ?: e;
                }
            };
        };

        private fun complete(value: T) {
            this.value = value
        }

        private fun exceptionally(exception: Throwable) {
            this.exception = exception;
        }

        override fun <R> then(mapping: (T) -> R): Request<R> = done(mapping = mapping);

        override fun catch(exceptional: (Throwable) -> Unit): Request<T> = done(exceptional) { it };

        private inline fun <R> done(noinline exceptional: (Throwable) -> Unit = { throw it; }, crossinline mapping: (T) -> R): Request<R> {
            return async result@ {
                try {
                    return@result mapping(get())
                } catch(e: Throwable) {
                    exceptional(e);
                    return@result null as R;
                }
            }
        }

        override fun get(): T {
            if (exception != null) {
                throw exception!!
            }
            return value ?: await();
        }

        private fun await(): T {
            if (exception != null) {
                throw exception!!;
            }

            val it = step!!;
            step = null;
            it.resume(Unit);

            return get();
        }


    };
}

inline fun <T> delegate(noinline exceptional: (Throwable) -> Unit = { throw it; }, crossinline complete: (T) -> Unit): Continuation<T> {
    return object : Continuation<T> {
        override val context: CoroutineContext = EmptyCoroutineContext;


        override fun resumeWithException(exception: Throwable) {
            exceptional(exception);
        }

        override fun resume(value: T) {
            complete(value);
        }
    }
}