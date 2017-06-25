package com.holi.oop;

import com.holi.oop.CompanionObjectTest.Literal.Token;
import org.junit.Test;

import static com.holi.oop.CompanionObjectTest.Literal.Token;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class JavaAccessCompanionObjectTest {

    @Test
    public void accessCompanionObjectInstanceThroughTheStaticMemberField() throws Throwable {
        assertThat(Token, instanceOf(Token.class));
    }
}
