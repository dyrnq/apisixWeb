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
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ApisixClientPostTest extends BaseJunit {



    @Test
    public void test_postRoute() throws ApisixSDKException {

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
            client.postRoute(r);
        }
    }

    @Test
    public void test_postUpstream() throws ApisixSDKException {
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
            client.postUpstream(upstream);

        }
    }

}
