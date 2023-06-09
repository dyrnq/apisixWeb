import cn.hutool.core.io.FileUtil;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.plugins.Echo;
import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ApisixClientTest extends BaseJunit {

    @Test
    public void test_putStreamRoute() throws ApisixSDKException {

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
            client.putStreamRoute(String.valueOf(i), r);

        }
    }

    @Test
    public void test_putRoute() throws ApisixSDKException {

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
            client.putRoute(String.valueOf(i), r);

        }
    }

    @Test
    public void test_putUpstream() throws ApisixSDKException {
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
            client.putUpstream(String.valueOf(i), upstream);

        }
    }

    @Test
    public void test_putService() throws ApisixSDKException {
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


            client.putService(String.valueOf(i), service);
        }
    }

    @Test
    public void test_putSecret() throws ApisixSDKException {

        for (int i = 1; i < 51; i++) {
            Secret secret = new Secret();
            secret.setUri("https://localhost/vault");
            secret.setPrefix("/apisix/kv");
            secret.setToken("343effad");
            client.putSecret(String.valueOf(i), "vault", secret);
        }

    }

    @Test
    public void test_putConsumer() throws ApisixSDKException {
        for (int i = 1; i < 51; i++) {
            Consumer consumer = new Consumer();
            consumer.setUsername(String.valueOf(i));
            consumer.setDesc(String.valueOf(i));
            //consumer.setGroupId(i+"");
            client.putConsumer(String.valueOf(i), consumer);
        }
    }

    @Test
    public void test_putConsumerGroup() throws ApisixSDKException {
        for (int i = 1; i < 51; i++) {
            ConsumerGroup consumer = new ConsumerGroup();
            consumer.setDesc(String.valueOf(i));
            //consumer.setGroupId(i+"");
            java.util.Map map = new HashMap();
            Echo e = new Echo();
            e.afterBody = "<!-- apisix-HTML-mark -->";
            map.put("echo", e);
            consumer.setPlugins(map);
            client.putConsumerGroup(String.valueOf(i), consumer);
        }

    }

    @Test
    public void test_putPluginConfig() throws ApisixSDKException {
        for (int i = 1; i < 51; i++) {
            PluginConfig pluginConfig = new PluginConfig();
            pluginConfig.setDesc(String.valueOf(i));
            java.util.Map map = new HashMap();
            Echo e = new Echo();
            e.afterBody = "<!-- apisix-HTML-mark -->";
            map.put("echo", e);
            pluginConfig.setPlugins(map);
            client.putPluginConfig(String.valueOf(i), pluginConfig);
        }
    }

    @Test
    public void test_putGlobalRule() throws ApisixSDKException {
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
            client.putGlobalRule(String.valueOf(i), globalRule);
        }


    }

    @Test
    public void test_putProto() throws ApisixSDKException, IOException {
        for (int i = 1; i < 20; i++) {
            Proto proto = new Proto();
            proto.setContent(IOUtils.toString(new FileInputStream(new File("src/test/resources/demo.proto")), StandardCharsets.UTF_8));
            client.putProto(String.valueOf(i), proto);
        }
    }

    @Test
    public void test_putSSL() throws ApisixSDKException, InvalidNameException, CertificateException, IOException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        for (int i = 1; i < 51; i++) {


            String file = "src/test/resources/server";
            //file="src/test/resources/server";
            SSL ssl = new SSL();
            ssl.setCert(FileUtil.readString(new File(file + ".crt"), StandardCharsets.UTF_8));
            ssl.setKey(FileUtil.readString(new File(file + ".key"), StandardCharsets.UTF_8));
            X509Certificate x509Cert = CertUtils.loadCertificate(new File(file + ".crt"));
            String[] sniArray = CertUtils.extractSNI(x509Cert);
            ssl.setSnis(Arrays.asList(sniArray));
            ssl.setStatus(1);
            client.putSSL(String.valueOf(i), ssl);

        }
    }
}
