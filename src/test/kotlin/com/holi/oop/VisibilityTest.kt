package com.holi.oop

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import org.junit.Test
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class VisibilityTest {

    class Property {
        private val privacy: Int = 1;
        internal val internal: Int = 2;
        public val public: Int = 3;
    }

    @Test
    fun `getters always have the same visibility as its property`() {
        val owner = Property();

        val visibilities = Property::class.memberProperties.onEach { it.isAccessible = true; }
                .sortedBy { it.get(owner) as Int }
                .map { "${it.getter.visibility}" };

        assert.that(visibilities, equalTo(listOf("PRIVATE", "INTERNAL", "PUBLIC")));
    }
}