import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.plugins.Echo;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApisixClientListTest extends BaseJunit{

    @Test
    public void test_listPlugins() throws ApisixSDKExcetion {
        client.listPlugins();
    }


    @Test
    public void test_listSSLs() throws ApisixSDKExcetion {
        client.listSSLs();
    }
    @Test
    public void test_listPluginConfigs() throws ApisixSDKExcetion {
        client.listPluginConfigs();
    }

    @Test
    public void test_listSecrets() throws ApisixSDKExcetion {
        client.listSecrets();
    }

    @Test
    public void test_listConsumers() throws ApisixSDKExcetion {
        client.listConsumers();
    }
    @Test
    public void test_listConsumerGroups() throws ApisixSDKExcetion {
        client.listConsumerGroups();
    }
    @Test
    public void test_listRoutes() throws ApisixSDKExcetion {
        List<Route> listRoute = client.listRoutes();

        for (Route rr : listRoute) {
            System.out.println(rr.getName());
        }
    }


    @Test
    public void test_listStreamRoutes() throws ApisixSDKExcetion {
        List<StreamRoute> listRoute = client.listStreamRoutes();

        for (StreamRoute rr : listRoute) {
            System.out.println(rr.getId());
        }
    }

    @Test
    public void test_listUpstreams() throws ApisixSDKExcetion {
        List<Upstream> listRoute = client.listUpstreams();

        for (Upstream rr : listRoute) {
            System.out.println(rr.getName());
        }
    }

}
