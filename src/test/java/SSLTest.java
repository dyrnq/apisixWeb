import cn.hutool.core.io.FileUtil;
import com.dyrnq.apisix.domain.SSL;
import org.junit.Test;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SSLTest extends BaseJunit{


    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;


    private static X509Certificate loadCertificate(String file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(in);
        }
    }

    @Test
    public void test_CreateSSL() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        SSL ssl = new SSL();
        ssl.setCert(FileUtil.readString(new File("cert-by-ca.crt"), Charset.forName("UTF-8")) );
        ssl.setKey(FileUtil.readString(new File("cert-by-ca.key"), Charset.forName("UTF-8")) );
        X509Certificate x509Cert = loadCertificate("cert-by-ca.crt");
        String subjectName = x509Cert.getSubjectX500Principal().getName();
        LdapName ldapName = new LdapName(subjectName);
        String cnValue=null;
        for (Rdn rdn : ldapName.getRdns()) {
            if (rdn.getType().equalsIgnoreCase("CN")) {
                cnValue = rdn.getValue().toString();
                // Do something with the CN value
                break;
            }
        }
        System.out.println("CN: " + cnValue);
        List<String> sni = new ArrayList<String>();
        sni.add(cnValue);

        Collection<?> altNames = x509Cert.getSubjectAlternativeNames();
        if (altNames != null) {
            for (Object altName : altNames) {
                System.out.println("SNI: " + altName);
                sni.add(altName+"");
            }
        }
        ssl.setSnis(sni);
        ssl.setStatus(1);
        client.putSSL("1", ssl);
    }
}
