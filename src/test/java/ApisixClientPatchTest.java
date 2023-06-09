import cn.hutool.core.io.FileUtil;
import com.dyrnq.apisix.ApisixSDKException;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

public class ApisixClientPatchTest extends BaseJunit {

    @Test
    public void test_patchRouteRaw() throws ApisixSDKException {
        String rawData = FileUtil.readString(new File("src/test/resources/route-data-patch.json"), Charset.forName("UTF-8"));
        client.patchRouteRaw("11", rawData);
    }


}
