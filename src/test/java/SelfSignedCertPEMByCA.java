import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;

public class SelfSignedCertPEMByCA {
    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }


    @Test
    public void test_createCertByCA() throws Exception {
        // 加载 CA 证书
        X509Certificate caCert = CertUtils.loadCertificate(new File("src/test/resources/ca.crt"));
        // 加载 CA 密钥
        PrivateKey caKey = CertUtils.load(new File("src/test/resources/ca.key"));

        // Generate the key pair
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", BC);
        kpGen.initialize(2048);
        KeyPair pair = kpGen.generateKeyPair();
        PrivateKey privKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        Date notBefore = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24); // 证书有效期从一天前开始
        Date notAfter = new Date(notBefore.getTime() + 3650L * 24L * 60L * 60L * 1000L); // 10 years validity

        // 构造 X.509 证书请求
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                new X500Name("CN=hello.com"), // 填写主题名称
                publicKey
        );
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner signer = csBuilder.build(caKey);
        PKCS10CertificationRequest csr = p10Builder.build(signer);

        // 根据证书请求生成证书
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                caCert, // 使用 CA 的证书颁发新的证书
                new BigInteger(64, new SecureRandom()), // 生成随机序列号
                notBefore,
                notAfter,
                csr.getSubject(), // 使用证书请求中的主题名称
                publicKey // 使用证书请求中的公钥
        );
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));
        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false)); // 非 CA 证书
        X509CertificateHolder certHolder = certBuilder.build(signer);

        // 将新生成的证书保存到文件中
        JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
        converter.setProvider(org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        X509Certificate newCert = converter.getCertificate(certHolder);
        IOUtils.write(CertUtils.content(newCert), new FileOutputStream(new File("src/test/resources/cert-by-ca.crt")));
        IOUtils.write(CertUtils.content(privKey), new FileOutputStream(new File("src/test/resources/cert-by-ca.key")));

    }

}
