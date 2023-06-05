import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import org.junit.Test;

import java.util.List;

public class ApisixClientDelTest extends BaseJunit{

    @Test
    public void test_delRoute() throws ApisixSDKException {
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
