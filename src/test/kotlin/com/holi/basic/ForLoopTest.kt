package com.holi.basic

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.util.*

/**
 * Created by holi on 6/6/17.
 */
class ForLoopTest {
    @Test
    fun `for-each loop`() {
        val numbers = arrayOf(1, 2, 3);
        val result = ArrayList<Int>();

        for (it in numbers) {
            result += it;
        }

        assert.that(result, equalTo(arrayListOf(1, 2, 3)));
    }

    @Test
    fun `iterates range`() {
        val result = ArrayList<Int>();

        for (it in 1..3) {
            result += it;
        }

        assert.that(result, equalTo(arrayListOf(1, 2, 3)));
    }

    @Test
    fun `supports fail fast feature`() {
        val list = arrayListOf(1, 2);
        val snippets = {
            for (item in list) {
                list += item * 3;
            }
        };

        assert.that(snippets, throws(isA<ConcurrentModificationException>()));
    }
}