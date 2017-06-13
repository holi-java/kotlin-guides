package com.holi.features;

import java.util.function.Supplier
import kotlin.coroutines.experimental.*

@RestrictsSuspension
interface Producer<in T> {
    suspend fun yield(value: T);
}

fun <T> produce(context: CoroutineContext = EmptyCoroutineContext, building: suspend Producer<T>.() -> Unit): Supplier<T> {
    val (NOT_READY, READY, DONE) = arrayOf(-1, 2, 3);
    val producer = object : Producer<T>, Continuation<Unit>, Supplier<T> {
        @JvmField var value: T? = null;
        @JvmField var step: Continuation<Unit>? = null;
        @JvmField var state: Int = NOT_READY;
        override fun get(): T {
            when (state) {
                READY -> return take();
                DONE -> throw NoSuchElementException();
            }
            val step = step!!;
            this.step = null;
            step.resume(Unit);
            return get();
        }

        private fun take(): T {
            state = NOT_READY;
            val it = value as T;
            value = null;
            return it;
        }

        override fun resume(value: Unit) {
            state = DONE;
        }

        override suspend fun yield(value: T) {
            this.state = READY;
            this.value = value;
            return suspendCoroutine<Unit> {
                step = it;
            };
        }

        override fun resumeWithException(exception: Throwable) {
            throw exception;
        }

        override val context = EmptyCoroutineContext;
    };

    producer.step = building.createCoroutine(producer, producer).run {
        return@run context[Async]?.start(this) ?: this;
    };

    return producer;
}