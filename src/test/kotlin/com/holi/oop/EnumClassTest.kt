package com.holi.oop

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.lessThan
import org.junit.Test

class EnumClassTest {
    enum class Color {RED, GREEN, BLUE }

    @Test
    fun `get enum constant by its name`() {
        assert.that(Color.valueOf("RED"), equalTo(Color.RED));
    }

    enum class Size(val bytes: Long) {
        KB(1024), MB(1024 * 1024);
    }

    @Test
    fun `enum class can be initialized with properties`() {
        assert.that(Size.KB.bytes, equalTo(1024L));
    }


    enum class Format {
        STRING {
            override fun format(source: Any) = source.toString();
        };

        abstract fun format(source: Any): String;
    }

    @Test
    fun `creates anonymous class for enum constant`() {
        assert.that(Format.STRING.format(1), equalTo("1"));
    }

    @Test
    fun `enum has implemented Comparable interface with the natural order`() {
        assert.that(Size.KB, lessThan(Size.MB));
    }
}


