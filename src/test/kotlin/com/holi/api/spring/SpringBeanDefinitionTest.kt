package com.holi.api.spring

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@[RunWith(SpringRunner::class) ContextConfiguration]
class SpringBeanDefinitionTest {

    @[Autowired Suppress("SpringKotlinAutowiring")]
    lateinit var foo: String;
    @[Autowired Suppress("SpringKotlinAutowiring")]
    lateinit var fuzz: String;

    @Test
    fun property() {
        assert.that(foo, equalTo("bar"));
    }

    @Test
    fun factory() {
        assert.that(fuzz, equalTo("buzz"));
    }


    @Configuration
    open class SpringTestConfig {
        @get:Bean("foo") open val foo by lazy { "bar" };

        @Bean("fuzz") open fun fuzz() = "buzz";
    }
}

