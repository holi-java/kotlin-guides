package com.holi.features

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ThisExpressionTest {

    inner class InnerClass {
        val test = this@ThisExpressionTest;
        val self = this;
        val lambdaWithReceiver = bind<Int, Any> { this };
        val lambdaWithoutReceiver = { this };

        fun <T, R> bind(block: T.() -> R) = block;
    };

    val it = InnerClass();

    @Test
    fun `default this scopes`() {
        assert.that(it.self, equalTo(it));
        assert.that(it.lambdaWithReceiver(0), equalTo<Any>(0));
        assert.that(it.lambdaWithoutReceiver(), equalTo<Any>(it));
    }

    @Test
    fun `reference the scoped instance by this@label`() {
        assert.that(it.test, equalTo(this));
    }
}