package com.holi.oop

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.lang.reflect.Modifier.FINAL
import java.lang.reflect.Modifier.PRIVATE
import kotlin.reflect.jvm.javaField

class PropertiesTest {

    @Test
    fun `default property fields is private final`() {
        class Property(val value: Int = 2)

        assertThat(Property()::value.javaField!!.modifiers, equalTo(PRIVATE or FINAL));
    }

    @Test
    fun readonly() {
        class Readonly(val value: Int);

        val it = Readonly(2);

        assert.that(it.value, equalTo(2));
    }

    @Test
    fun mutable() {
        class Mutable(var value: Int);

        val it = Mutable(3);
        assert.that(it.value, equalTo(3));

        it.value = 4;
        assert.that(it.value, equalTo(4));
    }


    @Test
    fun getters() {
        class Getter {
            val value get() = "foo";
        }

        val it = Getter();

        assert.that(it.value, equalTo("foo"));
    }


    @Test
    fun `backing field by using implicit field variable`() {
        class BackingSetter {
            var value: String? = ""
                set(value) {
                    field = value?.toUpperCase();
                }
        }

        val it = BackingSetter();
        assert.that(it.value, equalTo(""));

        it.value = "bar";
        assert.that(it.value, equalTo("BAR"));
    }


    @Test
    fun `backing field by another explicit field`() {
        class PrivacyBackingSetter {
            private var privacy = 1;
            var value: Int get() = privacy
                set(it) {
                    privacy = it * 2;
                }

        }

        val it = PrivacyBackingSetter();
        assert.that(it.value, equalTo(1));

        it.value = 3;
        assert.that(it.value, equalTo(6));
    }


    @Test
    fun `lazy initialized property`() {
        class LaziedProperty {
            var id: Int = 1;
            val value: Int by lazy value@ { return@value id++; };
        }

        val it = LaziedProperty();

        assert.that(it.value, equalTo(1));
        assert.that(it.value, equalTo(1));
    }


    @Test
    fun `late-init properties`() {
        class LateInitializedProperty {
            lateinit var late: String;
        }

        val it = LateInitializedProperty();
        assert.that({ it.late }, throws(isA<UninitializedPropertyAccessException>()));

        it.late = "foo";
        assert.that(it.late, equalTo("foo"));

        it.late = "bar";
        assert.that(it.late, equalTo("bar"));
    }
}