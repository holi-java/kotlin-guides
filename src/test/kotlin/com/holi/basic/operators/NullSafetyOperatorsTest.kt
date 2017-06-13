package com.holi.basic.operators

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test

class NullSafetyOperatorsTest {
    val foo: String? = "bar";
    val nil: String? = null;

    @Test
    fun `safe call`() {
        assert.that(foo?.length, equalTo(3));
        assert.that(nil?.length, absent());
        assert.that(foo?.toUpperCase()?.reversed(), equalTo("RAB"));
        assert.that(nil?.toUpperCase()?.reversed(), absent());
    }

    @Test
    fun `unsafe call`() {
        assert.that(foo!!.length, equalTo(3));
        assert.that({ nil!!.length }, throws(isA<NullPointerException>()));
    }
}