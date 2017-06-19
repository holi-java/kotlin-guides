package com.holi.api.stream

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors.toList
import java.util.stream.Stream
import java.util.stream.StreamSupport

class StreamTest {
    @Test
    fun exceptionally() {
        val result = Stream.of("BAD", "1").peek { Integer.parseInt(it) }.
                exceptionally { ex -> "${ex.message}" }.
                map { it.toString() }.collect(toList());

        assert.that(result, equalTo(listOf("""For input string: "BAD"""", "1")));
    }
}

inline fun <T> Stream<T>.exceptionally(crossinline handler: (Throwable) -> T) = stream { spliterator().exceptionally(handler); }

inline fun <T, R> Stream<T>.stream(block: Stream<T>.() -> Spliterator<R>) = StreamSupport.stream(block(), isParallel)!!;

inline fun <T> Spliterator<T>.exceptionally(crossinline handler: (Throwable) -> T) = object : Spliterator<T> by this {
    override fun tryAdvance(action: Consumer<in T>?): Boolean {
        try {
            return this@exceptionally.tryAdvance(action);
        } catch(ex: Exception) {
            action!!.accept(handler(ex));
            return true;
        }
    }
}