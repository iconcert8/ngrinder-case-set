package serverperformcomparison

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

import static net.grinder.script.Grinder.grinder
import static org.hamcrest.Matchers.is

@RunWith(GrinderRunner)
class ApacheMonolithicCreate {
    public static GTest test
    public static HTTPRequest request

    @BeforeProcess
    static void beforeProcess() {
        HTTPRequestControl.setConnectionTimeout(300000)
        test = new GTest(1, "apache-monolithic-create")
        request = new HTTPRequest()
    }

    @BeforeThread
    void beforeThread() {
        test.record(this, "test")
        grinder.statistics.delayReports = true
    }

    @Test
    void test() {
        HTTPResponse response = request.POST("http://127.0.0.1:8001/create", [username: "apache", password: "apache1234"])
        MatcherAssert.assertThat(response.statusCode, is(200))
    }
}

