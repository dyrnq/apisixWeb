import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.*;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ApisixClientTest {

    public static void main(String[] args) throws ApisixSDKExcetion {
        // TODO Auto-generated method stub


        String url = "192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile(url, "", c);


        AdminClient client = new AdminClient(p);

        for(int i=1;i<11;i++) {
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
            client.putStreamRoute(""+i, r);

        }


        for(int i=1;i<11;i++) {
            Route r = new Route();
            r.setName("test"+i);
            r.setDesc("test"+i);
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
            client.putRoute(""+i, r);

        }

        for(int i=1;i<11;i++) {

            Upstream upstream = new Upstream();
            upstream.setType("roundrobin");
            upstream.setName("test"+i);
            upstream.setDesc("test"+i);
            upstream.setScheme("http");
            upstream.setTimeout(new Timeout(6, 6, 6));
            List<Node> list = new ArrayList<Node>();
            list.add(new Node("127.0.0.1", 8080, 100));
            list.add(new Node("127.0.0.1", 8081, 100));
            list.add(new Node("127.0.0.1", 8082, 100));
            upstream.setNodes(list);
            client.putUpstream(""+i, upstream);

        }

        for(int i=1;i<11;i++) {
            Service service = new Service();
            service.setName("test"+i);
            service.setDesc("test"+i);

            Upstream upstream = new Upstream();
            upstream.setType("roundrobin");
            upstream.setName("test"+i);
            upstream.setDesc("test"+i);
            upstream.setScheme("http");
            upstream.setTimeout(new Timeout(6, 6, 6));
            List<Node> list = new ArrayList<Node>();
            list.add(new Node("127.0.0.1", 8080, 100));
            list.add(new Node("127.0.0.1", 8081, 100));
            list.add(new Node("127.0.0.1", 8082, 100));
            upstream.setNodes(list);
            service.setUpstream(upstream);


            client.putService(""+i, service);
        }

        for(int i=1;i<11;i++) {
            SSL ssl = new SSL();
            ssl.setCert(cn.hutool.core.io.FileUtil.readString(new File("/data/work/solon-example/server.crt"), Charset.forName("UTF-8")));
            ssl.setKey(cn.hutool.core.io.FileUtil.readString(new File("/data/work/solon-example/server.key"), Charset.forName("UTF-8")));
            List<String> sni = new ArrayList<String>();
            sni.add("abc.com");
            ssl.setSnis(sni);
            client.putSSL(""+i, ssl);
        }
        client.listSSLs();
        client.listStreamRoutes();
        client.listPluginConfigs();

        Route r2 = client.getRoute("1");
        System.out.println(r2.getName());


        List<Route> listRoute = client.listRoutes();

        for (Route rr : listRoute) {
            System.out.println(rr.getName());
        }

        List<Upstream> list2 = client.listUpstreams();

        for (Upstream rr : list2) {
            System.out.println(rr.getName());
        }


        List<Service> list3 = client.listServices();

        for (Service rr : list3) {
            System.out.println(rr.getName());
        }


//        Upstream rr = client.getUpstream("555");
//        if (rr !=null ) System.out.println(rr.getName());

        Upstream u = new Upstream();
        u.setName("test");
        u.setDesc("test");
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(new Node("baidu.com", 111, 1000));
        u.setNodes(nodeList);
        client.putUpstream("666", u);


    }
}
