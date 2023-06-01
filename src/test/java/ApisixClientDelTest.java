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

public class ApisixClientDelTest extends BaseJunit{

    @Test
    public void test_delRoute() throws ApisixSDKExcetion {
        client.delRoute("1");
    }

    @Test
    public void test_cleanRoute() throws Exception{
        List<Route> listObj = client.listRoutes();

        for (Route rr : listObj) {
            client.delRoute(rr.getId());
        }
    }

    @Test
    public void test_cleanStreamRoute() throws Exception{
        List<StreamRoute> listObj = client.listStreamRoutes();

        for (StreamRoute rr : listObj) {
            client.delStreamRoute(rr.getId());
        }
    }

    @Test
    public void test_cleanUpstream() throws Exception{
        List<Upstream> listObj = client.listUpstreams();

        for (Upstream rr : listObj) {
            client.delUpstream(rr.getId());
        }
    }

    @Test
    public void test_cleanService() throws Exception{
        List<Service> listObj = client.listServices();

        for (Service rr : listObj) {
            client.delService(rr.getId());
        }
    }

    @Test
    public void test_cleanSecret() throws Exception{
        List<Secret> listObj = client.listSecrets();
        for (Secret rr : listObj) {
            client.delSecret(rr.getId());
        }
    }
    @Test
    public void test_cleanSSL() throws Exception{
        List<SSL> listObj = client.listSSLs();
        for (SSL rr : listObj) {
            client.delSSL(rr.getId());
        }
    }
}
