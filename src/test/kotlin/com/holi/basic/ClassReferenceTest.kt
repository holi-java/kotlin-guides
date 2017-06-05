package com.holi.basic

import com.taroid.knit.should
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Test
import kotlin.reflect.KClass

/**
 * Created by holi on 6/6/17.
 */
class ClassReferenceTest {

    @Test
    fun `get class from type`() {
        val type = Runnable::class;

        type.should(instanceOf(KClass::class.java));
    }

    @Test
    fun `get class from objects`() {
        val obj = Runnable {};

        obj.javaClass.interfaces.should be arrayOf(Runnable::class.java);
    }
}