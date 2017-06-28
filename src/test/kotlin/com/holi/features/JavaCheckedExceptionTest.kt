package com.holi.features

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.io.IOException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.lang.reflect.UndeclaredThrowableException
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

@Suppress("UNUSED_PARAMETER")
fun throwsAJavaCheckedException(proxy: Any, method: Method, args: Array<Any>?): Any? {
    throw IOException();
}


typealias Invocation = (Any, Method, Array<Any>?) -> Any?;
fun <T : Any> KClass<T>.proxying(handler: Invocation) = cast(Proxy.newProxyInstance(
        ClassLoader.getSystemClassLoader(),
        arrayOf(java),
        handler
));

class JavaCheckedExceptionTest {

    interface Executable {
        @Throws(IOException::class) fun invoke();
    }

    @Test
    fun `throws the original exception when error occurs if the function has declared that it will be throwing`() {
        assert.that({ Executable::class.proxying(::throwsAJavaCheckedException).invoke() }, throws(isA<IOException>()));
    }

    @Test
    fun `throws an UndeclaredThrowableException when error occurs if the function has not declared that it will be throwing`() {
        assert.that({ Runnable::class.proxying(::throwsAJavaCheckedException).run() }, throws(isA<UndeclaredThrowableException>()));
    }

    @Test
    fun `no exception occurs`() {
        assert.that(catching { 1 }.by { 2 }, equalTo(1));
    }


    @Test
    fun `return exceptional when error occurs`() {
        assert.that(catching { throw IllegalStateException() }.by { 2 }, equalTo(2));
    }

    @Test
    fun `catch the actual exception when error occurs`() {
        catching(Runnable::class.proxying(::throwsAJavaCheckedException)::run).only {
            assert.that(it, isA<IOException>());
        }
    }


}


inline fun <T> catching(crossinline block: () -> T): () -> T {
    return { block(); };
}

inline fun <T> (() -> T).by(exceptionally: (Throwable) -> T): T {
    return only { exceptionally(it) }!!
}

inline fun <T : R, R> (() -> T).only(exceptionally: (Throwable) -> R): R? {
    try {
        return invoke();
    } catch(e: UndeclaredThrowableException) {
        return exceptionally(e.cause ?: e);
    } catch(e: Exception) {
        return exceptionally(e);
    }
}
