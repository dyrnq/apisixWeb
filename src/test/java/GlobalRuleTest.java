import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.GlobalRule;
import com.dyrnq.apisix.plugins.Echo;
import com.dyrnq.apisix.plugins.Headers;
import com.dyrnq.apisix.plugins.ProxyRewrite;
import org.junit.Test;

import java.util.HashMap;

public class GlobalRuleTest extends BaseJunit {
    @Test
    public void test_GlobalRule() throws ApisixSDKException {
        GlobalRule globalRule = new GlobalRule();
        java.util.Map map = new HashMap();
        Echo e = new Echo();
        e.headers = new HashMap<String, String>();
        e.headers.put("hello", "world");
        e.afterBody = "<!-- 111 apisix-HTML-mark -->";
        map.put(Echo.PLUGIN_NAME, e);
        ProxyRewrite proxyRewrite = new ProxyRewrite();
        proxyRewrite.headers = new Headers();
        proxyRewrite.headers.set = new HashMap<>();
        proxyRewrite.headers.set.put("X-Real-IP", "$remote_addr");
        proxyRewrite.headers.set.put("X-Forwarded-For", "$proxy_add_x_forwarded_for");
        proxyRewrite.headers.set.put("X-Test-1", "test1");
        proxyRewrite.headers.set.put("X-Test-2", "test2");
        proxyRewrite.headers.set.put("X-Test-3", "test3");
        proxyRewrite.headers.set.put("X-Test-4", "test4");

        map.put(ProxyRewrite.PLUGIN_NAME, proxyRewrite);
        globalRule.setPlugins(map);
        //consumer.setGroupId(i+"");
        client.putGlobalRule("1", globalRule);
    }
}
