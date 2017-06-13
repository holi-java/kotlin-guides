package com.holi.oop

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.isA
import org.junit.Test


class CompanionObjectTest {

    interface Token<out T>;
    interface Variable {
        companion object Token : CompanionObjectTest.Token<Variable>;
    }

    enum class Literal {
        STRING, NUMBER;

        companion object Token : CompanionObjectTest.Token<Literal>;
    }

    @Test
    fun `types can be used as companion object`() {
        assert.that(Variable, isA<Token<*>>(equalTo(Variable.Token)));
        assert.that(Literal, isA<Token<*>>(equalTo(Literal.Token)));
    }


    class AnnotatedJvmStatic {
        companion object {
            @JvmStatic fun foo() = "bar"
        }
    }

    class UnannotatedJvmStatic {
        companion object {
            fun foo() = "baz"
        }
    }

    @Test
    fun `companion object members is able to access simplify by class qualifier`() {
        assert.that(AnnotatedJvmStatic.foo(), equalTo("bar"));
        assert.that(UnannotatedJvmStatic.foo(), equalTo("baz"));
    }

    @Test
    fun `delegates members to companion object's method if declare companion object members with @JvmStatic annotation`() {
        assert.that(UnannotatedJvmStatic::class.java.declaredMethods.map { it.name }, !hasElement("foo"));
        assert.that(AnnotatedJvmStatic::class.java.declaredMethods.map { it.name }, hasElement("foo"));
        assert.that(AnnotatedJvmStatic.Companion::class.java.declaredMethods.map { it.name }, hasElement("foo"));
    }
}