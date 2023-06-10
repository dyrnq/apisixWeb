import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.plugins.Ai;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PluginMetadataTest extends BaseJunit {


    @Test
    public void test_putPluginMetadata() throws ApisixSDKException {
        Map p = new HashMap();
        p.put("hello", "world");
        client.putPluginMetadata(Ai.PLUGIN_NAME, p);
    }

    @Test
    public void test_getPluginMetadata() throws ApisixSDKException {
        Map p = client.getPluginMetadata(Ai.PLUGIN_NAME);
    }

}
