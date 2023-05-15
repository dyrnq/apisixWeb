import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.plugins.Echo;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApisixClientDelTest {

    public static void main(String[] args) throws ApisixSDKExcetion {
        // TODO Auto-generated method stub


        String url = "192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile(url, "", c);


        AdminClient client = new AdminClient(p);

        client.delRoute("1");



    }
}
