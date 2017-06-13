package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ComponentNOperatorTest {

    @Test
    fun `component N for destructuring`() {
        val it = object {
            operator fun component1() = "foo"
            operator fun component2() = "bar"
            operator fun component3() = 3;
            operator fun component4() = 4;
        };

        val (first, second, third) = it;

        assert.that(first, equalTo("foo"));
        assert.that(second, equalTo("bar"));
        assert.that(third, equalTo(3));
    }
}