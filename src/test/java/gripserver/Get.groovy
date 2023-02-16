package gripserver

import net.grinder.script.GTest
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.ngrinder.http.HTTPRequest
import org.ngrinder.http.HTTPRequestControl
import org.ngrinder.http.HTTPResponse

@RunWith(GrinderRunner)
class Get {
    public static GTest test
    public static HTTPRequest request

    @BeforeProcess
    static void beforeProcess() {
        HTTPRequestControl.setConnectionTimeout(300000)
        test = new GTest(1, "grip-server-get")
        request = new HTTPRequest()
    }

    @BeforeThread
    void beforeThread() {
        test.record(this, "test")
        net.grinder.script.Grinder.grinder.statistics.delayReports = true
    }

    @Test
    void test() {
        HTTPResponse response = request.GET("http://127.0.0.1:8080/scraps/1?reqUserId=1")
        MatcherAssert.assertThat(response.statusCode, org.hamcrest.Matchers.is(200))
    }
}