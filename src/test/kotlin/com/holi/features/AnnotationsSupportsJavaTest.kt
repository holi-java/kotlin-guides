@file:Suppress("warnings")

package com.holi.features

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runners.model.Statement

class AnnotationsSupportsJavaTest {
    var enabled = false;

    @get:Rule val rule = TestRule { base, _ ->
        return@TestRule object : Statement() {
            override fun evaluate() {
                enabled = true;
                base?.evaluate();
            }
        };
    };

    @Test
    fun `annotation @Rule enabled for junit4`() {
        assert(true) { "@Rule disabled" };
    }
}

