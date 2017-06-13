package com.holi.oop

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import org.junit.Test

class SingletonTest {

    @Test
    fun `inline`() {
        val resource = object {
            val name = "jmx";
        };

        assert.that(resource.name, equalTo("jmx"));
    }

    @Test
    fun external() {
        assert.that(Resource.name, equalTo("jmx"));
    }

    object Resource {
        val name = "jmx";
    }
}