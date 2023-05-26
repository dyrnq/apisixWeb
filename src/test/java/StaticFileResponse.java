import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.plugins.ResponseRewrite;

import java.util.*;

public class StaticFileResponse {

    public static void main(String[] args) throws ApisixSDKExcetion {
        String url = "192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile(url, "", c);
        AdminClient client = new AdminClient(p);

        Route r =new Route();
        r.setName("StaticFileResponse");
        //List<String> host = new ArrayList<>();
//        host.add("");
        r.setUri("/test/index.html");
        //r.setHosts(host);
        Map<String, Object> map = new HashMap<>();
        ResponseRewrite responseRewrite = new ResponseRewrite();
        responseRewrite.body="<html>Jenkins</html>";
        map.put("response-rewrite",responseRewrite);
        r.setPlugins(map);

        client.putRoute("5000",r);
    }
}
