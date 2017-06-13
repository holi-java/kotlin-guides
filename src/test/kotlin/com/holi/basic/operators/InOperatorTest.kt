package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.*
import kotlin.coroutines.experimental.buildIterator

class InOperatorTest {

    @Test
    fun `'contains' operator for checking element whether is in a collection`() {
        val it = object {
            operator fun contains(value: Any?) = "foo" == value;
        };

        assert.that("foo" in it, equalTo(true));
        assert.that("bar" in it, equalTo(false));
    }


    @Test
    fun `'iterator' operator is used for for-each`() {
        val it = object {
            operator fun iterator(): Iterator<String> = listOf("foo").iterator();
        };

        val items = mutableListOf<String>();
        for (item in it) {
            items += item;
        }

        assert.that(items, equalTo(listOf("foo")));
    }

}