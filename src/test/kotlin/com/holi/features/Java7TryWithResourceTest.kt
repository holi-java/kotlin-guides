package com.holi.features

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test
import java.io.Closeable

class Java7TryWithResourceTest {

    @Test
    fun `close resource after used`() {
        var closed = false;
        val resource: Closeable = Closeable({ closed = true });

        assert.that(closed, equalTo(false));

        resource.use {};
        assert.that(closed, equalTo(true));
    }
}