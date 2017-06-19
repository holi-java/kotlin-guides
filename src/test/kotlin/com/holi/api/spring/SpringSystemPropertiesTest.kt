package com.holi.api.spring

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class SpringSystemPropertiesTest {

    @Value("#{systemProperties.debug != null}")
    var debug: Boolean = false;
    //                    ^--- using a default value to avoiding NPException

    @Test
    fun debugEnabled() {
        assert(debug);
    }

    @Configuration
    open class Config {
        init {
            System.setProperty("debug", "");
        }
    }
}
