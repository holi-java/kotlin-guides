package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class EqualityOperatorsTest {
    val it: Int? = 1;

    @Test
    fun `structural equality`() {
        assert(it == 1)
        assert(it != 2)
    }

    @Test
    fun `reference equality`() {
        assert(it === it)
        assert(it !== Integer(it.toString()) as Int?)
    }

}