package com.holi.basic.control_flow

import cancel
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import org.junit.Assert.fail
import org.junit.Test
import java.util.concurrent.CancellationException

class LabelTest {
    @Test(expected = CancellationException::class)
    fun `function return without @label is always return the function`() {
        val it = fun(): String {
            return "foo"
        };

        assert.that(it(), equalTo("foo"));
        cancel();
    }

    @Test
    fun `break loop`() {
        all@ while (true) {
            while (true) {
                break@all;
            }
            fail();
        }

        assert(true);
    }


    @Test(expected = ReachableException::class)
    fun `return lambda only by @label`() {
        val run = label@ {
            return@label;
        };

        run();
        throw ReachableException();
    }

    @Test
    fun `return with @label does not interrupt lambda`() {
        val actual = ArrayList<Int>();

        (1..3).forEach each@ {
            actual.add(it);
            if (it > 1) {
                return@each;
            }
        };

        assert.that(actual, equalTo(listOf(1, 2, 3)));
    }

    @Test
    fun `enable return from the nearest enclosing function by inline functions`() {
        inline {
            return;
        };

        fail();
    }

    @Test(expected = ReachableException::class)
    fun `return from a lambda by using @label with any function`() {
        uninline scope@ {
            return@scope;
        };

        throw ReachableException();
    }

    @Test(expected = ReachableException::class)
    fun `return from a lambda by using implicit @label with any function`() {
        uninline {
            return@uninline;
        };

        throw ReachableException();
    }

    @Test
    fun `return value from a lambda`() {
        val actual = inline {
            return@inline 1;
        };

        assert.that(actual, equalTo(1));
    }

    inline fun <R> inline(block: () -> R): R {
        return block();
    }

    fun <R> uninline(block: () -> R): R {
        return block();
    }

    class ReachableException : Exception() {}
}