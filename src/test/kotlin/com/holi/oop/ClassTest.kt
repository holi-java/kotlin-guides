package com.holi.oop

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.superclasses

class ClassTest {


    @Test
    fun `curly brackets can be omitted for empty class`() {
        class Empty;

        Empty();
    }

    @Test
    fun `constructor keyword can be omitted if neither visibility modifier nor annotations present`() {
        class Internal internal constructor();
        class Default;

        Internal();
        Default();
    }

    @Test
    fun `assign properties from primary constructor`() {
        class Primary(value: Int) {
            val value: Int = value;
        }

        val it = Primary(2);

        assert.that(it.value, equalTo(2));
    }

    @Test
    fun `initializing primary constructor with init block`() {
        class Primary(value: Int) {
            val value: Int;

            init {
                this.value = value;
            }
        }

        val it = Primary(1);

        assert.that(it.value, equalTo(1));
    }

    @Test
    fun `declares immutable property from primary constructor`() {
        class Immutable(val value: Int);

        val it = Immutable(3);

        assert.that(it.value, equalTo(3));
        assert.that(it::value is KMutableProperty<*>, equalTo(false));
    }

    @Test
    fun `declares mutable property from primary constructor`() {
        class Mutable(var value: Int);

        val it = Mutable(3);

        assert.that(it::value is KMutableProperty<*>, equalTo(true));
        assert.that(it.value, equalTo(3));

        it.value = 4;
        assert.that(it.value, equalTo(4));
    }

    @Test
    fun `secondary constructor`() {
        class Secondary {
            val value: Int;

            constructor(value: Int) {
                this.value = value;
            }
        }

        val it = Secondary(2);

        assert.that(it.value, equalTo(2));
    }

    @Test
    fun `delegating constructors by this keyword`() {
        class Constructors(val value: Int, val type: String) {
            constructor(value: Int) : this(value, "invalid");
        }

        val it = Constructors(1);

        assert.that(it.value, equalTo(1));
        assert.that(it.type, equalTo("invalid"));
    }

    @Test
    fun `all kotlin top class is derived from Any implicitly`() {
        class Top;

        assert.that(Top::class.superclasses, equalTo(listOf<KClass<*>>(Any::class)));
    }

    @Test
    fun `Any is the top class like as java_lang_Object`() {
        assert.that(Any::class.superclasses, isEmpty);
        assert.that(Any::class.java.name, equalTo("java.lang.Object"));
    }

    open class Base(open val value: Int);

    @Test
    fun `base type must be initialized on primary constructor if primary constructor present`() {
        class Subclass(value: Int) : Base(value);

        val it = Subclass(1);

        assert.that(it.value, equalTo(1));
        assert.that(it is Base, equalTo(true));
    }

    @Test
    fun `base type must be initialized on secondary constructor by super keyword if secondary constructor present`() {
        class Subclass : Base {
            constructor(value: Int) : super(value);
        };

        val it = Subclass(1);

        assert.that(it.value, equalTo(1));
        assert.that(it is Base, equalTo(true));
    }

    @Test
    fun `hiding base properties by overriding openable properties`() {
        class Subclass(override val value: Int) : Base(value + 1);

        val it = Subclass(2);

        assert.that(it.value, equalTo(2));
        assert.that(Base::value.get(it), equalTo(2));
    }

    @Test
    fun `overriding openable val properties with var properties`() {
        class Subclass(override var value: Int) : Base(value + 1);

        val it = Subclass(2);

        assert.that(it.value, equalTo(2));

        it.value = 3;
        assert.that(it.value, equalTo(3));
    }

    @Test
    fun `inherit multi implementations`() {
        class Subclass() : Superclass(), Valuable {
            override fun value() = super<Superclass>.value() + super<Valuable>.value();
        }

        val it = Subclass();

        assert.that(it.value(), equalTo(4));
    }

    open class Superclass {

        open fun value() = 1;
    }

    interface Valuable {

        fun value() = 3;
    }

    @Test
    fun `overriding properties from interface`() {
        class Subclass(override val value: Int) : Property<Int>;

        val it = Subclass(3);

        assert.that(it.value, equalTo(3));
    }



    interface Property<T> {
        val value: T;
    }
}