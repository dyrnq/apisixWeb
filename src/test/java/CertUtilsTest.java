import com.dyrnq.utils.CertUtils;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertUtilsTest {


    @Test
    public void test_readCert() throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream("src/test/resources/server.crt");
        java.security.cert.Certificate c = cf.generateCertificate(in);
        X509Certificate t = (X509Certificate) c;
        System.out.println(t.getVersion());
        System.out.println(t.getSerialNumber().toString(16));
        System.out.println(t.getSubjectDN());
        System.out.println(t.getIssuerDN());
        System.out.println(t.getNotBefore());
        System.out.println(t.getNotAfter());
        System.out.println(t.getSigAlgName());
        byte[] sig = t.getSignature();
        System.out.println(new BigInteger(sig).toString(16));
        PublicKey pk = t.getPublicKey();
        byte[] pkenc = pk.getEncoded();
        for (int i = 0; i < pkenc.length; i++) {
            System.out.print(pkenc[i] + ",");
        }
        in.close();
    }

    @Test
    public void test_loadPrivateKey() throws Exception{
        PrivateKey privateKey = CertUtils.load(new File("src/test/resources/server.key"));
        String contentPrivateKey = CertUtils.content(privateKey);
        System.out.println(contentPrivateKey);
    }

    @Test
    public void test_loadCertificate() throws Exception{
        X509Certificate cert = CertUtils.loadCertificate(new File("src/test/resources/server.crt"));
        String contentCert = CertUtils.content(cert);
        System.out.println(contentCert);
    }

}
