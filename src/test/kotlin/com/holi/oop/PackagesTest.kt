package com.holi.oop

import org.junit.Test

/**
 * Created by holi on 6/6/17.
 */
class PackagesTest {
    @Test
    fun `does not requires to match directories and packages`() {
        Class.forName("stub.Package");
    }
}