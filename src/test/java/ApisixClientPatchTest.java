import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

public class ApisixClientPatchTest extends BaseJunit{

    @Test
    public void test_patchRouteRaw() throws ApisixSDKExcetion {
        String rawData= cn.hutool.core.io.FileUtil.readString(new File("route-data-patch.json"), Charset.forName("UTF-8"));
        client.patchRouteRaw("11",rawData);
    }



}
