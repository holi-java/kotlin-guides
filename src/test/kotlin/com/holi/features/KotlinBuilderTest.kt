package com.holi.features

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class KotlinBuilderTest {
    abstract class Initializer {
        protected abstract val out: StringBuilder;

        operator fun String.unaryPlus(): Unit {
            out.append(this)
        }

        operator fun Initializer.unaryPlus(): Unit {
            this@Initializer.out.append(this);
        }
    }

    fun buildString(init: Initializer.() -> Unit): String {
        val initializer: Initializer = object : Initializer() {
            override val out = StringBuilder()
            override fun toString(): String = out.toString()
        }
        return initializer.apply(init).toString();
    }

    @Test
    fun `build a string`() {
        val result = buildString {
            +"foot"
            +"ball"
        };

        assert.that(result, equalTo("football"));
    }

    @Test
    fun `nested`() {
        val result = buildString root@ {
            +"ball "
            +buildString {
                +"foot";
                +this@root;
            }
            +buildString {
                +"basket";
                +this@root;
            }
        };

        assert.that(result, equalTo("ball football basketball football "));
    }
}