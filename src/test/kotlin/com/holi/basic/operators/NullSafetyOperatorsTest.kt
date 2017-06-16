package com.holi.basic.operators

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test
import kotlin.jvm.internal.Ref.ObjectRef

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

    @Test
    fun `short-circuiting`() {
        val none: ObjectRef<Unit>? = null;
        val value: ObjectRef<Unit>? = ObjectRef();
        var calls = 0;
        fun call() {
            calls++;
        }


        none?.element = call();
        assert.that(calls, equalTo(0));

        value?.element = call();
        assert.that(calls, equalTo(1));

        value?.element = call();
        assert.that(calls, equalTo(2));
    }

    @Test
    fun `copy the reference on short-circuiting`() {
        var it: ObjectRef<Int>? = ObjectRef();
        val expected = it;
        var received: Any? = null;

        fun call(): Int {
            received = it;
            it = null;
            return 1;
        }

        it?.element = call();

        assert.that(received, sameInstance<Any?>(expected));
        assert.that(it, absent());
    }
}