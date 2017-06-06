package com.holi.basic

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import org.junit.Test

/**
 * Created by holi on 6/6/17.
 */
class StringTemplatesTest {
    @Test
    fun `interpolates variables`() {
        val foo = "bar";

        val it = "foo is $foo";

        assert.that(it, isA<String>() and isA<java.lang.String>() and equalTo("foo is bar"));
    }
}