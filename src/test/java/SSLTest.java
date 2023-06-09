import cn.hutool.core.io.FileUtil;
import com.dyrnq.apisix.domain.SSL;
import com.dyrnq.utils.CertUtils;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class SSLTest extends BaseJunit {


    @Test
    public void test_CreateSSL() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        String file = "src/test/resources/example-domain";
        //file="src/test/resources/server";
        SSL ssl = new SSL();
        ssl.setCert(FileUtil.readString(new File(file + ".crt"), Charset.forName("UTF-8")));
        ssl.setKey(FileUtil.readString(new File(file + ".key"), Charset.forName("UTF-8")));
        X509Certificate x509Cert = CertUtils.loadCertificate(file + ".crt");
        String[] sniArray = CertUtils.extractSNI(x509Cert);
        ssl.setSnis(Arrays.asList(sniArray));
        ssl.setStatus(1);
        client.putSSL("1", ssl);
    }
}
