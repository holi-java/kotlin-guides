package com.holi.api.spring

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.*
import org.springframework.stereotype.Component
import org.springframework.test.context.junit4.SpringRunner

@[RunWith(SpringRunner::class) Suppress("SpringKotlinAutowiring")]
class SpringInjectionTest {
    @[Autowired Qualifier("property")]
    lateinit var property: String;

    @Autowired
    lateinit var constructor: Constructor;

    @Autowired
    lateinit var constructorParameter: ConstructorParameter;

    @field:[Autowired Qualifier("field")]
    lateinit var field: String;

    @set:[Autowired Qualifier("setter")]
    lateinit var setter: String; private set;

    @set:[Autowired]
    @setparam:Qualifier("setparam")
    lateinit var setterParameter: String; private set;

    @Test
    fun constructor() {
        assert.that(constructor.value, equalTo("constructor"));
    }

    @Test
    fun `constructor parameter`() {
        assert.that(constructorParameter.value, equalTo("constructor param"));
    }

    @Test
    fun property() {
        assert.that(property, equalTo("property"));
    }

    @Test
    fun field() {
        assert.that(field, equalTo("field"));
    }

    @Test
    fun setter() {
        assert.that(setter, equalTo("setter"));
    }

    @Test
    fun `setter param`() {
        assert.that(setterParameter, equalTo("param"));
    }

    @Component
    class Constructor @Autowired constructor(@Qualifier("init") val value: String);

    @Component
    class ConstructorParameter(@param:[Autowired Qualifier("param")] val value: String);


    @Configuration
    @Import(ConstructorParameter::class, Constructor::class)
    open class Config {
        @Bean open fun init() = "constructor";
        @Bean open fun param() = "constructor param";
        @Bean open fun property() = "property";
        @Bean open fun field() = "field";
        @Bean open fun setter() = "setter";
        @Bean open fun setparam() = "param";
    }
}

