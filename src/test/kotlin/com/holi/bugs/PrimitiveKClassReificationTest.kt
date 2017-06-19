package com.holi.bugs

import org.junit.Test
import java.util.*
import kotlin.reflect.KClass


class PrimitiveKClassReificationTest {

    @Test
    @Bug("https://youtrack.jetbrains.com/issue/KT-17748")
    fun primitive() {
        assert(!typeOf<Int>(Int::class));
    }

    @Test
    fun `object`() {
        assert(typeOf<Date>(Date::class));
    }
}

inline fun <reified T> typeOf(type: KClass<*>) = T::class == type;