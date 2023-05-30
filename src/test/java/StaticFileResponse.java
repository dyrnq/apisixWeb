import cn.hutool.core.lang.hash.Hash;
import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.plugins.Gzip;
import com.dyrnq.apisix.plugins.Headers;
import com.dyrnq.apisix.plugins.ResponseRewrite;
import org.junit.Test;

import java.util.*;

public class StaticFileResponse extends BaseJunit{

    @Test
    public void test_StaticFileResponse() throws ApisixSDKExcetion {
        Route r =new Route();
        r.setName("StaticFileResponse");
        r.setUri("/test/index.html");
        Map<String, Object> map = new HashMap<>();
        ResponseRewrite responseRewrite = new ResponseRewrite();
        responseRewrite.body="<html>Jenkins，中文测试</html>";
        responseRewrite.statusCode=200;
        Headers headers= new Headers();
        headers.set =  new HashMap<>();
        headers.set.put("Content-Type","text/html; charset=utf-8");
        responseRewrite.headers = headers;
        map.put("response-rewrite",responseRewrite);
        Gzip gzip = new Gzip();
        gzip.types="*";
        map.put("gzip",gzip);

        r.setPlugins(map);

        client.putRoute("5000",r);
    }
}
