import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.junit.Test;

public class SelfSignedCertPEMByCA {
    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;
    static{
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    private static X509Certificate loadCACertificate() throws Exception {
        try (InputStream in = new FileInputStream("ca.crt")) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(in);
        }
    }

    private static PrivateKey loadCAKey() throws Exception{
//        try (Reader reader = new FileReader("ca.key")) {
//            PEMParser parser = new PEMParser(reader);
//            Object obj = parser.readObject();
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//            return converter.getPrivateKey(((PEMKeyPair) obj).getPrivateKeyInfo());
//        }

        try (Reader reader = new FileReader("ca.key")) {
            PEMParser parser = new PEMParser(reader);
            Object obj = parser.readObject();
            if (obj instanceof PEMKeyPair) {
                // 将 PEM 密钥对转换为 JCE 格式的密钥对
                KeyPair keyPair = new JcaPEMKeyConverter().setProvider("BC").getKeyPair((PEMKeyPair) obj);
                return keyPair.getPrivate();
            } else {
                throw new IllegalArgumentException("Unsupported PEM object.");
            }
        }

    }
    @Test
    public void test_createCertByCA() throws Exception {


        // 加载 CA 证书
        X509Certificate caCert = loadCACertificate();

        // 加载 CA 密钥
        PrivateKey caKey = loadCAKey();


        // Generate the key pair
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", BC);
        kpGen.initialize(2048);
        KeyPair pair = kpGen.generateKeyPair();
        PrivateKey privKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();


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
                new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24), // 证书有效期从一天前开始
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365), // 证书有效期为一年
                csr.getSubject(), // 使用证书请求中的主题名称
                publicKey // 使用证书请求中的公钥
        );
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));
        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false)); // 非 CA 证书
        X509CertificateHolder certHolder = certBuilder.build(csBuilder.build(caKey));

        // 将新生成的证书保存到文件中
        JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
        converter.setProvider(org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        X509Certificate newCert = converter.getCertificate(certHolder);
        CertUtils.pemWriter(newCert,privKey,"cert-by-ca");
    }

}
