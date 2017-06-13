package com.holi.basic

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test


/**
 * Created by holi on 6/6/17.
 */
class VariablesTest {
    @Test
    fun `mutable variables`() {
        var it = 1;
        assert.that(it, equalTo(1));

        it = 2;
        assert.that(it, equalTo(2));
    }

    @Test
    fun `immutable variables`() {
        val it = 1;

        assert.that(it, equalTo(1));
    }

    @Test
    fun `immutable test can be lazy initialized`() {
        val it: Int;

        it = 3;

        assert.that(it, equalTo(3));
    }

    @Test
    fun nullable() {
        val it: Int? = null;

        assert.that(it, absent());
    }

    @Test
    fun `lazy initializing`() {
        val items = listOf("foo", "bar").iterator();

        val it: String by lazy(items::next);

        assert.that(it, equalTo("foo"));
        assert.that(it, equalTo("foo"));
    }

    @Test
    fun `constant can be declared at top level`() {
        assert.that(global, equalTo(1));
    }

    object Value {
        const val value = 3;
    };
    @Test
    fun `constant can be declared in objects`() {
        assert.that(Value.value, equalTo(3));
    }

}

const val global: Int = 1;
