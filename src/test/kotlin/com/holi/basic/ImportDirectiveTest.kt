package com.holi.basic

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.util.ArrayList as JList;

class ImportDirectiveTest {
    @Test
    fun `alias the identifier by 'as' keyword`() {
        val list = JList<Int>();

        list.add(1);

        assert.that(list, equalTo(listOf(1)));
    }
}