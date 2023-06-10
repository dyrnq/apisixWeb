import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@FixMethodOrder(MethodSorters.JVM)
public class ApisixClientDelTest extends BaseJunit {


    @Test
    public void test_delRoute() throws ApisixSDKException {
        client.delRoute("1");
    }

    @Test
    public void test_cleanRoute() throws Exception {
        List<Route> listObj = client.listRoutes();

        for (Route rr : listObj) {
            client.delRoute(rr.getId());
        }
    }

    @Test
    public void test_cleanStreamRoute() throws Exception {
        List<StreamRoute> listObj = client.listStreamRoutes();

        for (StreamRoute rr : listObj) {
            client.delStreamRoute(rr.getId());
        }
    }

    @Test
    public void test_cleanUpstream() throws Exception {
        List<Upstream> listObj = client.listUpstreams();

        for (Upstream rr : listObj) {
            client.delUpstream(rr.getId());
        }
    }

    @Test
    public void test_cleanService() throws Exception {
        List<Service> listObj = client.listServices();

        for (Service rr : listObj) {
            client.delService(rr.getId());
        }
    }

    @Test
    public void test_cleanSecret() throws Exception {
        List<Secret> listObj = client.listSecrets();
        for (Secret rr : listObj) {
            client.delSecret(rr.getId());
        }
    }

    @Test
    public void test_cleanSSL() throws Exception {
        List<SSL> listObj = client.listSSLs();
        for (SSL rr : listObj) {
            client.delSSL(rr.getId());
        }
    }

    @Test
    public void test_cleanProto() throws Exception {
        List<Proto> listObj = client.listProtos();
        for (Proto rr : listObj) {
            client.delProto(rr.getId());
        }
    }

    @Test
    public void test_cleanGlobalRule() throws Exception {
        List<GlobalRule> listObj = client.listGlobalRules();
        for (GlobalRule rr : listObj) {
            client.delGlobalRule(rr.getId());
        }
    }

    @Test
    public void test_cleanConsumer() throws Exception {
        List<Consumer> listObj = client.listConsumers();
        for (Consumer rr : listObj) {
            client.delConsumer(rr.getUsername());
        }
    }

    @Test
    public void test_cleanConsumerGroup() throws Exception {
        List<ConsumerGroup> listObj = client.listConsumerGroups();
        for (ConsumerGroup rr : listObj) {
            client.delConsumerGroup(rr.getId());
        }
    }

    @Test
    public void test_cleanPluginConfig() throws Exception {
        List<PluginConfig> listObj = client.listPluginConfigs();
        for (PluginConfig rr : listObj) {
            client.delPluginConfig(rr.getId());
        }
    }


    @Test
    public void test_cleanAll() throws Exception {
        this.test_cleanSSL();
        this.test_cleanRoute();
        this.test_cleanStreamRoute();
        this.test_cleanGlobalRule();
        this.test_cleanConsumer();
        this.test_cleanConsumerGroup();
        this.test_cleanProto();
        this.test_cleanService();
        this.test_cleanSecret();
        this.test_cleanUpstream();
        this.test_cleanPluginConfig();
    }
}
