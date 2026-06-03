import cn.hutool.core.io.FileUtil;
import com.dyrnq.apisix.domain.Client;
import com.dyrnq.apisix.domain.SSL;
import com.dyrnq.utils.CertUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SSLTest extends BaseJunit {

    @Test
    public void test_CreateSSL() throws Exception {

        String file = "src/test/resources/example-domain";
        // file = "src/test/resources/server";
        SSL ssl = new SSL();
        ssl.setCert(FileUtil.readString(new File(file + ".crt"), StandardCharsets.UTF_8));
        ssl.setKey(FileUtil.readString(new File(file + ".key"), StandardCharsets.UTF_8));
        X509Certificate x509Cert = CertUtils.loadCertificate(new File(file + ".crt"));
        String[] sniArray = CertUtils.extractSNI(x509Cert);
        ssl.setSnis(Arrays.asList(sniArray));
        ssl.setStatus(1);
        client.putSSL("1", ssl);
    }

    @Test
    public void test_CreateClientSSL() throws Exception {

        String file = "src/test/resources/example-domain";
        file = "src/test/resources/server";
        SSL ssl = new SSL();
        ssl.setCert(FileUtil.readString(new File(file + ".crt"), StandardCharsets.UTF_8));
        ssl.setKey(FileUtil.readString(new File(file + ".key"), StandardCharsets.UTF_8));

        Client c = new Client();
        c.setCa(FileUtil.readString(new File(file + ".crt"), StandardCharsets.UTF_8));
        c.setDepth(1);
        c.setSkipMtlsUriRegex(new String[] {"/aa", "/b"});
        ssl.setClient(c);

        ssl.setStatus(1);
        ssl.setType("client");
        client.putSSL("2", ssl);
    }
}
