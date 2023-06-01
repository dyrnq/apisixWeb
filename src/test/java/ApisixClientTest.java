import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.plugins.Echo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApisixClientTest extends BaseJunit {

    @Test
    public void test_putStreamRoute() throws ApisixSDKExcetion {

        for (int i = 1; i < 51; i++) {
            StreamRoute r = new StreamRoute();
//            r.setName("test"+i);
//            r.setDesc("test"+i);
//            r.setUri("/*");
            Upstream upstream = new Upstream();
            upstream.setType("roundrobin");
            upstream.setName("test");
            upstream.setScheme("http");
            upstream.setTimeout(new Timeout(6, 6, 6));
            List<Node> list = new ArrayList<Node>();
            list.add(new Node("127.0.0.1", 18081, 100));
            list.add(new Node("127.0.0.1", 18082, 100));
            list.add(new Node("127.0.0.1", 18083, 100));
            list.add(new Node("127.0.0.1", 18084, 100));
            upstream.setNodes(list);
            r.setUpstream(upstream);
            client.putStreamRoute("" + i, r);

        }
    }

    @Test
    public void test_putRoute() throws ApisixSDKExcetion {

        for (int i = 1; i < 51; i++) {
            Route r = new Route();
            r.setName("test" + i);
            r.setDesc("test" + i);
            r.setUri("/*");
            Upstream upstream = new Upstream();
            upstream.setType("roundrobin");
            upstream.setName("test");
            upstream.setScheme("http");
            upstream.setTimeout(new Timeout(6, 6, 6));
            List<Node> list = new ArrayList<Node>();
            list.add(new Node("127.0.0.1", 18081, 100));
            list.add(new Node("127.0.0.1", 18082, 100));
            list.add(new Node("127.0.0.1", 18083, 100));
            list.add(new Node("127.0.0.1", 18084, 100));
            upstream.setNodes(list);
            r.setUpstream(upstream);
            client.putRoute("" + i, r);

        }
    }

    @Test
    public void test_putUpstream() throws ApisixSDKExcetion {
        for (int i = 1; i < 51; i++) {

            Upstream upstream = new Upstream();
            upstream.setType("roundrobin");
            upstream.setName("test" + i);
            upstream.setDesc("test" + i);
            upstream.setScheme("http");
            upstream.setTimeout(new Timeout(6, 6, 6));
            List<Node> list = new ArrayList<Node>();
            list.add(new Node("127.0.0.1", 8080, 100));
            list.add(new Node("127.0.0.1", 8081, 100));
            list.add(new Node("127.0.0.1", 8082, 100));
            upstream.setNodes(list);
            client.putUpstream("" + i, upstream);

        }
    }

    @Test
    public void test_putService() throws ApisixSDKExcetion {
        for (int i = 1; i < 51; i++) {
            Service service = new Service();
            service.setName("test" + i);
            service.setDesc("test" + i);

            Upstream upstream = new Upstream();
            upstream.setType("roundrobin");
            upstream.setName("test" + i);
            upstream.setDesc("test" + i);
            upstream.setScheme("http");
            upstream.setTimeout(new Timeout(6, 6, 6));
            List<Node> list = new ArrayList<Node>();
            list.add(new Node("127.0.0.1", 8080, 100));
            list.add(new Node("127.0.0.1", 8081, 100));
            list.add(new Node("127.0.0.1", 8082, 100));
            upstream.setNodes(list);
            service.setUpstream(upstream);


            client.putService("" + i, service);
        }
    }

    @Test
    public void test_putSecret() throws ApisixSDKExcetion {

        for (int i = 1; i < 51; i++) {
            Secret secret = new Secret();
            secret.setUri("https://localhost/vault");
            secret.setPrefix("/apisix/kv");
            secret.setToken("343effad");
            client.putSecret("" + i, "vault", secret);
        }

    }

    @Test
    public void test_putConsumer() throws ApisixSDKExcetion {
        for (int i = 1; i < 51; i++) {
            Consumer consumer = new Consumer();
            consumer.setUsername(i + "");
            consumer.setDesc(i + "");
            //consumer.setGroupId(i+"");
            client.putConsumer("" + i, consumer);
        }
    }

    @Test
    public void test_putConsumerGroup() throws ApisixSDKExcetion {
        for (int i = 1; i < 51; i++) {
            ConsumerGroup consumer = new ConsumerGroup();
            consumer.setDesc(i + "");
            //consumer.setGroupId(i+"");
            java.util.Map map = new HashMap();
            Echo e = new Echo();
            e.afterBody = "<!-- apisix-HTML-mark -->";
            map.put("echo", e);
            consumer.setPlugins(map);
            client.putConsumerGroup("" + i, consumer);
        }

    }

    @Test
    public void test_putPluginConfig() throws ApisixSDKExcetion {
        for (int i = 1; i < 51; i++) {
            PluginConfig pluginConfig = new PluginConfig();
            pluginConfig.setDesc(i + "");
            java.util.Map map = new HashMap();
            Echo e = new Echo();
            e.afterBody = "<!-- apisix-HTML-mark -->";
            map.put("echo", e);
            pluginConfig.setPlugins(map);
            client.putPluginConfig("" + i, pluginConfig);
        }
    }

    @Test
    public void test_putGlobalRule() throws ApisixSDKExcetion {
        for (int i = 1; i < 20; i++) {
            GlobalRule globalRule = new GlobalRule();
            java.util.Map map = new HashMap();
            Echo e = new Echo();
            e.headers = new HashMap<String, String>();
            e.headers.put("hello", "world");
            e.afterBody = "<!-- apisix-HTML-mark -->";
            map.put("echo", e);
            globalRule.setPlugins(map);
            //consumer.setGroupId(i+"");
            client.putGlobalRule("" + i, globalRule);
        }


    }
}
