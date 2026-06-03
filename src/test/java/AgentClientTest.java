import cn.hutool.core.codec.Base64Decoder;
import com.dyrnq.apisix.agentclient.AgentClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgentClientTest {
    AgentClient agentClient = null;
    String token = "your-secret-api-key";
    String host = "http://192.168.66.100:5980";

    @BeforeEach
    public void init() {
        agentClient = new AgentClient();
    }

    @Test
    public void testReadConfig() throws Exception {
        String s = agentClient.readConfig(host, token);
        System.out.println(Base64Decoder.decodeStr(s));
    }
}
