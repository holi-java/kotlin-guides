package com.holi.oop

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.util.*

class NullPointerExceptionOccurrenceTest {

    @Test
    fun `an explicit all throw new NullPointerException()`() {
        assert.that({ throw NullPointerException() }, throws(isA<NullPointerException>()));
    }

    @Test
    fun `use !! operator with null reference`() {
        var ref = null;

        assert.that({ ref!!::class }, throws(isA<NullPointerException>()));
    }

    @Test
    fun `calling external java code may raising NullPointerException`() {
        assert.that({ Optional.empty<String>().orElse(null).length }, throws(isA<NullPointerException>()));
    }

    @Test
    fun `some data inconsistency with regard to initialization`() {
        val initializing: () -> Unit = {
            object {
                val value: String = "${this.length()}";
                fun length() = value.length;
            };
        }
        assert.that(initializing, throws(isA<NullPointerException>()));
    }
}