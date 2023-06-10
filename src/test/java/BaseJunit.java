import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.DefaultCredential;
import com.dyrnq.apisix.profile.DefaultProfile;
import com.dyrnq.apisix.profile.Profile;
import org.junit.Before;

public class BaseJunit {


    AdminClient client = null;

    @Before
    public void initClient() {
        if (client == null) {
            String url = "192.168.66.100:9180";
            Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
            Profile p = DefaultProfile.getProfile(url, "", c);
            client = new AdminClient(p);
        }
    }

}
