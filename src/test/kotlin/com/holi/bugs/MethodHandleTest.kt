package com.holi.bugs

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType.methodType

class MethodHandleTest {
    val parseInt = MethodHandles.lookup().findStatic(
            Integer::class.java,
            "parseInt",
            methodType(Int::class.java, String::class.java)
    )!!;

    @[Test Bug("https://youtrack.jetbrains.com/issue/IDEA-154967")]
    fun `don't supports invoke()`() {
        assert.that({ parseInt("1") as Int }, throws(isA<ClassCastException>()));
    }


    @Test
    fun `supports invokeWithArguments()`() {
        assert.that(parseInt.invokeWithArguments("1"), equalTo<Any>(1));
    }
}