package com.holi.api.coroutine

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.sameInstance
import com.natpryce.hamkrest.throws
import org.junit.Ignore
import org.junit.Test
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
    @Ignore
    fun `async can capture exception from the block`() {
        val expected = IllegalStateException();
        val it = async<Any> {
            throw expected;
        };

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
    fun `chain`() {
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


}


interface Request<out T> {
    fun get(): T;
    fun <R> then(mapping: (T) -> R): Request<R>;
    fun catch(exceptional: (Throwable) -> Unit)
}

private val executor: ExecutorService = ForkJoinPool(20);
fun <T> async(block: suspend () -> T): Request<T> {
    return object : Request<T> {
        @Volatile var value: T? = null;

        var request: Continuation<Unit>? = block.createCoroutine(delegate(resume =this::complete)).let {
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

        override fun <R> then(mapping: (T) -> R): Request<R> = async<R> {
            mapping(get());
        };

        override fun catch(exceptional: (Throwable) -> Unit): Unit {
            try {
                get();
            } catch(e: Throwable) {
                exceptional(e);
            }
        }


        override fun get(): T {
            return value ?: wait();
        }

        private fun wait(): T {
            val it = request!!;
            request = null;
            it.resume(Unit);
            return value!!;
        }


    };
}

inline fun <T> delegate(noinline exceptional: (Throwable) -> Unit = { throw it; }, crossinline resume: (T) -> Unit): Continuation<T> {
    return object : Continuation<T> {
        override val context: CoroutineContext = EmptyCoroutineContext;


        override fun resumeWithException(exception: Throwable) {
            exceptional(exception);
        }

        override fun resume(value: T) {
            resume(value);
        }
    }
}