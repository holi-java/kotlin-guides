@file:[JvmName("CoroutineContexts") JvmMultifileClass]

package com.holi.features

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.Future
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext


interface Async : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Async>;

    fun <T> async(completion: Continuation<T>): Continuation<T>;

    fun start(completion: Continuation<Unit>): Continuation<Unit> {
        return async(completion).apply { resume(Unit); };
    }
}


open class ThreadPoolCoroutineContext : AbstractCoroutineContextElement(Async), Async {
    val executor by lazy(ForkJoinPool::commonPool);
    override fun <T> async(completion: Continuation<T>): Continuation<T> {

        return object : Continuation<T> {
            override val context: CoroutineContext = EmptyCoroutineContext;
            var task: Future<*>? = null;

            override fun resume(value: T) {
                dispatcher(completion::resume).invoke(value);
            }

            override fun resumeWithException(exception: Throwable) {
                dispatcher(completion::resumeWithException).invoke(exception);
            }

            private fun <T> dispatcher(resume: (T) -> Unit): (T) -> Unit {
                when (task) {
                    null -> return { value -> task = executor.submit { resume(value) }; }
                    else -> return { task.apply { task = null;this!!.get(); } }
                }
            }

        };
    }
}


object CommonPool : ThreadPoolCoroutineContext();