package com.holi.api.coroutine

import kotlin.coroutines.experimental.CoroutineContext

class CoroutineContextTest {
    val context: CoroutineContext = object: CoroutineContext {
        override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
            TODO("not implemented")
        }

        override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? {
            TODO("not implemented")
        }

        override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
            TODO("not implemented")
        }

        override fun plus(context: CoroutineContext): CoroutineContext {
            TODO("not implemented")
        }
    }

}