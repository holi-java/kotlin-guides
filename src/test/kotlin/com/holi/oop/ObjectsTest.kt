package com.holi.oop

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class ObjectsTest {
    @Test
    fun `it's able to access properties in local objects`() {
        val it = object {
            val value = 3;
        }

        assert.that(it.value, equalTo(3));
    }

    private val privacy = object {
        val value = 3;
    }

    @Test
    fun `it's able to access properties in private objects`() {
        assert.that(privacy.value, equalTo(3));
    }

    public val public = privacy;

    @Test
    fun `it isn't able to access properties in private objects`() {
        assertThat(this::public.javaField!!.type, equalTo<Class<*>>(Any::class.java));
        assertThat(this::privacy.javaField!!.type, equalTo<Class<*>>(privacy::class.java));
    }


}

