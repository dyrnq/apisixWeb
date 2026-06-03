import cn.hutool.core.io.FileUtil;
import com.dyrnq.apisix.ApisixSDKException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class ApisixClientPatchTest extends BaseJunit {

    @Test
    public void test_patchRouteRaw() throws ApisixSDKException {
        String rawData =
                FileUtil.readString(new File("src/test/resources/route-data-patch.json"), StandardCharsets.UTF_8);
        client.patchRouteRaw("11", rawData);
    }
}
