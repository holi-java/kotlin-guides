package com.holi.matchers

import com.taroid.knit.should
import org.junit.Assert.fail
import org.junit.Test

/**
 * Created by holi on 6/5/17.
 */
class KNitTest {
    @Test
    fun equality() {
        "foo".should be "foo";
    }

    @Test
    fun `not be`() {
        "foo".should notBe "bar";
    }

    @Test(expected = AssertionError::class)
    fun `fails with expected exception`() {
        fail("ok");
    }
}