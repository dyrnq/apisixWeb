import cn.hutool.core.util.RuntimeUtil;
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
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SelfSignedEd25519 extends BaseJunit {
    @Test
    public void test_Ed448() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Generate a new key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("Ed448", "BC");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Generate a self-signed X.509 certificate
        JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("Ed448");
        ContentSigner contentSigner = signerBuilder.build(keyPair.getPrivate());
        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
        X500Name issuer = new X500Name("CN=hello.com");
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
        File certFile = new File("src/test/resources/Ed448-cert.pem");
        File keyFile = new File("src/test/resources/Ed448-key.pem");

        // 将证书和私钥输出到文件
        IOUtils.write(CertUtils.content(cert), new FileOutputStream(certFile));
        IOUtils.write(CertUtils.content(keyPair.getPrivate()), new FileOutputStream(keyFile));

        System.out.println(RuntimeUtil.execForStr("openssl x509 -in src/test/resources/Ed448-cert.pem -text -noout"));
    }

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
        X500Name issuer = new X500Name("CN=hello.com");
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

        System.out.println(RuntimeUtil.execForStr("openssl x509 -in src/test/resources/ed25519-cert.pem -text -noout"));
    }

    @Test
    public void test_printSignature() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Set<String> algorithms = new HashSet<>();
        for (Provider provider : Security.getProviders()) {
            provider.getServices().stream()
                    .filter(service -> "Signature".equals(service.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(algorithms::add);
        }
        algorithms.forEach(System.out::println);

    }

    /**
     * 密钥对算法和签名算法不一样
     *
     * @throws Exception
     */
    @Test
    public void test_x25519() throws Exception {
        // 生成密钥对
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 加载 CA 证书
        X509Certificate caCert = CertUtils.loadCertificate(new File("src/test/resources/example-ca.crt"));
        // 加载 CA 密钥
        PrivateKey caKey = CertUtils.load(new File("src/test/resources/example-ca.key"));


        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("X25519", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec("X25519"));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();


        Date notBefore = Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
        Date notAfter = Date.from(LocalDate.now().plusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC));


        // 构造 X.509 证书请求
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                new X500Name("CN=hello.com"), // 填写主题名称
                publicKey
        );
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner signer = csBuilder.build(caKey);
        PKCS10CertificationRequest csr = p10Builder.build(signer);

        // 根据证书请求生成证书
        X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                caCert, // 使用 CA 的证书颁发新的证书
                new BigInteger(64, new SecureRandom()), // 生成随机序列号
                notBefore,
                notAfter,
                csr.getSubject(), // 使用证书请求中的主题名称
                publicKey // 使用证书请求中的公钥
        );
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        certificateBuilder.addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));
        certificateBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false)); // 非 CA 证书
        X509CertificateHolder certHolder = certificateBuilder.build(signer);

        // 将新生成的证书保存到文件中
        JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
        converter.setProvider(org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        X509Certificate newCert = converter.getCertificate(certHolder);
        IOUtils.write(CertUtils.content(newCert), new FileOutputStream(new File("src/test/resources/X25519.crt")));
        IOUtils.write(CertUtils.content(privKey), new FileOutputStream(new File("src/test/resources/X25519.key")));

        System.out.println(RuntimeUtil.execForStr("openssl x509 -in src/test/resources/X25519.crt -text -noout"));

    }
}