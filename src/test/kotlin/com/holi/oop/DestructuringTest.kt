package com.holi.oop

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class DestructuringTest {
    class Cluster<out T>(vararg val values: T) {
        operator fun component1(): T = values[0];
        operator fun component2(): T = values[1];
        operator fun component3(): T = values[2];
    }


    @Test
    fun `use underscore variable '_' for unused variables`() {
        val (_, second) = Cluster(1, 2, 3);
        //   ^--- variable _ can't be used

        assert.that(second, equalTo(2));
    }

    @Test
    fun `destructuring in lambda parameters`() {
        val lambda = { (first, second): Cluster<String> -> listOf(first, second) };

        assert.that(lambda(Cluster("foo", "bar", "baz")), equalTo(listOf("foo", "bar")));
    }
}