import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertUtils {

    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static X509Certificate loadCertificate(String file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(in);
        }
    }


    public static PrivateKey loadPrivateKey(String file) throws Exception{
//        try (Reader reader = new FileReader("ca.key")) {
//            PEMParser parser = new PEMParser(reader);
//            Object obj = parser.readObject();
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//            return converter.getPrivateKey(((PEMKeyPair) obj).getPrivateKeyInfo());
//        }

        try (Reader reader = new FileReader(file)) {
            PEMParser parser = new PEMParser(reader);
            Object obj = parser.readObject();
            if (obj instanceof PEMKeyPair) {
                // 将 PEM 密钥对转换为 JCE 格式的密钥对
                KeyPair keyPair = new JcaPEMKeyConverter().setProvider(BC).getKeyPair((PEMKeyPair) obj);
                return keyPair.getPrivate();
            } else {
                throw new IllegalArgumentException("Unsupported PEM object.");
            }
        }

    }

    public static void pemWriter(Object cert,Object privKey,String namePrefix) throws IOException {

        StringWriter sw = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(sw);
        pemWriter.writeObject(cert);
        pemWriter.flush();
        pemWriter.close();

        String certPem = sw.toString();
        System.out.println(certPem);

        FileOutputStream out = new FileOutputStream(namePrefix+".crt");
        out.write(certPem.getBytes());
        out.close();


        sw = new StringWriter();
        pemWriter = new JcaPEMWriter(sw);
        pemWriter.writeObject(privKey);
        pemWriter.flush();
        pemWriter.close();

        String keyPem = sw.toString();
        System.out.println(keyPem);

        out = new FileOutputStream(namePrefix+".key");
        out.write(keyPem.getBytes());
        out.close();
    }
}
