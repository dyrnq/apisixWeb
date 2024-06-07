import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.plugins.Gzip;
import com.dyrnq.apisix.plugins.Headers;
import com.dyrnq.apisix.plugins.Redirect;
import com.dyrnq.apisix.plugins.ResponseRewrite;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StaticFileResponse extends BaseJunit {

    @Test
    public void test_StaticFileResponse() throws ApisixSDKException {
        Route r = new Route();
        r.setName("StaticFileResponse");
        r.setUri("/test/index.html");
        Map<String, Object> map = new HashMap<>();
        ResponseRewrite responseRewrite = new ResponseRewrite();
        responseRewrite.body = "<html>Jenkins，中文测试</html>";
        responseRewrite.statusCode = 200;
        Headers headers = new Headers();
        headers.set = new HashMap<>();
        headers.set.put("Content-Type", "text/html; charset=utf-8");
        responseRewrite.headers = headers;

        Redirect redirect = new Redirect();
        redirect.httpToHttps = true;
        map.put(ResponseRewrite.PLUGIN_NAME, responseRewrite);
        map.put(Redirect.PLUGIN_NAME, redirect);
        Gzip gzip = new Gzip();
        gzip.types = "*";
        map.put(Gzip.PLUGIN_NAME, gzip);

        r.setPlugins(map);

        client.putRoute("5000", r);
    }
}
