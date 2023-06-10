import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import org.junit.Test;

import java.util.List;

public class ApisixClientListTest extends BaseJunit {

    @Test
    public void test_listPlugins() throws ApisixSDKException {
        client.listPlugins();
    }


    @Test
    public void test_listSSLs() throws ApisixSDKException {
        client.listSSLs();
    }

    @Test
    public void test_listPluginConfigs() throws ApisixSDKException {
        client.listPluginConfigs();
    }

    @Test
    public void test_listSecrets() throws ApisixSDKException {
        client.listSecrets();
    }

    @Test
    public void test_listConsumers() throws ApisixSDKException {
        client.listConsumers();
    }

    @Test
    public void test_listConsumerGroups() throws ApisixSDKException {
        client.listConsumerGroups();
    }

    @Test
    public void test_listRoutes() throws ApisixSDKException {
        List<Route> listRoute = client.listRoutes();

        for (Route rr : listRoute) {
            System.out.println(rr.getName());
        }
    }


    @Test
    public void test_listStreamRoutes() throws ApisixSDKException {
        List<StreamRoute> listRoute = client.listStreamRoutes();

        for (StreamRoute rr : listRoute) {
            System.out.println(rr.getId());
        }
    }

    @Test
    public void test_listUpstreams() throws ApisixSDKException {
        List<Upstream> listRoute = client.listUpstreams();

        for (Upstream rr : listRoute) {
            System.out.println(rr.getName());
        }
    }

    @Test
    public void test_listProtos() throws ApisixSDKException {
        List<Proto> listRoute = client.listProtos();
    }

    @Test
    public void test_listServices() throws ApisixSDKException {
        List<Service> listRoute = client.listServices();
    }

}
