import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;

public class SelfSignedEd25519 extends BaseJunit {


    @Test
    public void test_Ed25519() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Generate a new key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("Ed25519", "BC");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Generate a self-signed X.509 certificate
        JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("Ed25519");
        ContentSigner contentSigner = signerBuilder.build(keyPair.getPrivate());
        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
        X500Name issuer = new X500Name("CN=Your Name");
        X500Name subject = issuer;

        // 构建有效期
        Date startDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // 一天前
        Date endDate = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000); // 一年后


        // 构建证书请求
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer,
                new java.math.BigInteger(1, new SecureRandom()),
                startDate,
                endDate,
                subject,
                keyPair.getPublic()
        );

        X509CertificateHolder certHolder = certBuilder.build(contentSigner);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter().setProvider("BC");
        java.security.cert.X509Certificate cert = certConverter.getCertificate(certHolder);

        // Save the certificate and private key to disk
        File certFile = new File("src/test/resources/ed25519-cert.pem");
        File keyFile = new File("src/test/resources/ed25519-key.pem");

        // 将证书和私钥输出到文件
        IOUtils.write(CertUtils.content(cert), new FileOutputStream(certFile));
        IOUtils.write(CertUtils.content(keyPair.getPrivate()), new FileOutputStream(keyFile));

//        FileUtils.writeStringToFile(certFile, "-----BEGIN CERTIFICATE-----\n" + Hex.toHexString(cert.getEncoded()) + "\n-----END CERTIFICATE-----", "UTF-8");
//        FileUtils.writeStringToFile(keyFile, "-----BEGIN PRIVATE KEY-----\n" + Hex.toHexString(keyPair.getPrivate().getEncoded()) + "\n-----END PRIVATE KEY-----", "UTF-8");
//
//        System.out.println("Successfully generated Ed25519 certificate and private key.");
    }
}
