import com.holi.oop.CompanionObjectTest
import com.holi.oop.CompanionObjectTest.Literal.Token
import org.junit.Test

import static com.holi.oop.CompanionObjectTest.Literal.Token as TokenInstance

class GroovyAccessingCompanionObjectTest {
    @Test
    void "access companion object instance through the static member field"() throws Throwable {
        assert CompanionObjectTest.Literal.@Token instanceof Token;
    }


    @Test
    void "can't access companion object instance through imported static members"() throws Throwable {
        assert TokenInstance instanceof Class<Token>;
    }

}
