package com.holi.oop

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.isA
import org.junit.Test
import kotlin.reflect.KClass
import com.holi.oop.CompanionObjectTest.Literal.Token as LiteralToken;

typealias VariableToken = CompanionObjectTest.Variable.Token;


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

    @Test
    fun `can't refer companion object class by `() {
        assert.that(Literal.Token, !isA<KClass<*>>());
        assert.that(Literal.Token, isA<Literal.Token>());
    }


    @Test
    fun `can't refer companion object class by import statement`() {
        assert.that(LiteralToken, !isA<KClass<*>>());
        assert.that(LiteralToken, isA<Literal.Token>());
    }


    @Test
    fun `can't refer companion object class by typealias keyword`() {
        assert.that(VariableToken, !isA<KClass<*>>());
        assert.that(VariableToken, isA<VariableToken>());
    }

    @Test
    fun `refer companion object class by class`() {
        assert.that(VariableToken::class, isA<KClass<Token<*>>>());
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