package com.holi.basic.operators

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.sameInstance
import org.junit.Test

class `HowToSolveTheStrangeOperator+=ConflictTest` {
    @Test
    fun `+= is equivalent to plusAssign operator for immutable variable`() {
        val list = arrayListOf(1);
        val addend = arrayListOf(2);
        val snapshot = list;

        list += addend;
        //   ^--- if the list is an immutable variable, but list is a mutable List.
        //        then the code above is equivalent to `list.plusAssign(addend)`.

        assert.that(list, sameInstance(snapshot));
    }

    @Test
    fun `+= is equivalent to plus operator for mutable variable`() {
        var list: List<Int> = arrayListOf(1);
        val addend = arrayListOf(2);
        val snapshot = list;

        list += addend;
        //   ^--- to solving the conflict `+=` between `plus` and `plusAssign`,
        //  the list must be defined as an immutable List for a mutable variable.
        //  then the code above is equivalent to `list.plus(addend)`.

        assert.that(list, equalTo(listOf(1, 2)))
        assert.that(snapshot, equalTo(listOf(1)))
    }
}