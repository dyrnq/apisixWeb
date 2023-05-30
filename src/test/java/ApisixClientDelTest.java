import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.plugins.Echo;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApisixClientDelTest extends BaseJunit{

    @Test
    public void test_delRoute() throws ApisixSDKExcetion {
        client.delRoute("1");
    }
}
