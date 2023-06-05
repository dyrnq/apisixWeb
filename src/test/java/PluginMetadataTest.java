import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.PluginMetadata;
import org.junit.Test;

import java.util.Map;

public class PluginMetadataTest extends BaseJunit {


    @Test
    public void test_getPluginMetadata() throws ApisixSDKException {
        //List<Map> list = client.listPlugins();
        Map p = client.getPluginMetadata("ai");
    }
}
