package com.holi.api.spring

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner


@[RunWith(SpringRunner::class) ContextConfiguration]
class SpringPrimaryBeanTest {
    @[Autowired Suppress("SpringKotlinAutowiring")]
    lateinit var primary: String;

    @[Autowired Qualifier("common") Suppress("SpringKotlinAutowiring")]
    lateinit var common: String;

    @Test
    fun primary() {
        assert.that(primary, equalTo("main"));
    }

    @Test
    fun common() {
        assert.that(common, equalTo("plain"));
    }

    @Configuration
    open class Config {
        @[Primary Bean] open fun main() = "main";
        @Bean("common") open fun plain() = "plain";
    }
}