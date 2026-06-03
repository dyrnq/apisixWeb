import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.dyrnq.utils.CertUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SelfSignedCertPEM {
    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Test
    public void test_createCert()
            throws NoSuchAlgorithmException, CertificateException, OperatorCreationException, IOException,
                    NoSuchProviderException {
        // Generate the key pair
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", BC);
        kpGen.initialize(2048);

        KeyPair pair = kpGen.generateKeyPair();
        PrivateKey privKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.ST, "GD");
        nameBuilder.addRDN(BCStyle.C, "CN");
        nameBuilder.addRDN(BCStyle.L, "SZ");
        nameBuilder.addRDN(BCStyle.O, "vihoo");
        nameBuilder.addRDN(BCStyle.OU, "dev");
        nameBuilder.addRDN(BCStyle.CN, "test.com");
        nameBuilder.addRDN(BCStyle.EmailAddress, "yy@vivo.com");

        // Build the X500Name object
        X500Name subject = nameBuilder.build();

        Date notBefore = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24); // 证书有效期从一天前开始
        Date notAfter = new Date(notBefore.getTime() + 3650L * 24L * 60L * 60L * 1000L); // 10 years validity
        // Create the certificate
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                subject, new BigInteger(64, new SecureRandom()), notBefore, notAfter, subject, publicKey);

        // Add additional domain names as Subject Alternative Names (SANs)
        List<GeneralName> sanList = new ArrayList<>();
        sanList.add(new GeneralName(GeneralName.dNSName, "www.example.com"));
        sanList.add(new GeneralName(GeneralName.dNSName, "example.com"));
        sanList.add(new GeneralName(GeneralName.iPAddress, "192.168.0.1"));

        GeneralNames subjectAltNames = new GeneralNames(sanList.toArray(new GeneralName[0]));
        certBuilder.addExtension(Extension.subjectAlternativeName, false, subjectAltNames);
        // Set the basic constraints extension
        BasicConstraints basicConstraints = new BasicConstraints(true); // CA flag is true
        certBuilder.addExtension(new Extension(Extension.basicConstraints, true, basicConstraints.getEncoded()));

        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(publicKey);
        AuthorityKeyIdentifier authorityKeyIdentifier =
                new JcaX509ExtensionUtils().createAuthorityKeyIdentifier(publicKey);

        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        certBuilder.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);

        // Sign the certificate
        ContentSigner sigGen = new JcaContentSignerBuilder("SHA256WithRSA").build(privKey);
        X509CertificateHolder certificateHolder = certBuilder.build(sigGen);
        X509Certificate cert = new JcaX509CertificateConverter().setProvider(BC).getCertificate(certificateHolder);

        IOUtils.write(CertUtils.content(cert), new FileOutputStream(new File("src/test/resources/example.crt")));
        IOUtils.write(CertUtils.content(privKey), new FileOutputStream(new File("src/test/resources/example.key")));
    }

    /**
     * 创建密钥对测试
     *
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws OperatorCreationException
     * @throws IOException
     * @throws NoSuchProviderException
     */
    @Test
    public void test_createRSAKeyPair()
            throws NoSuchAlgorithmException, CertificateException, OperatorCreationException, IOException,
                    NoSuchProviderException {

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", BC);
        kpGen.initialize(2048);

        KeyPair pair = kpGen.generateKeyPair();
        PrivateKey privKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        IOUtils.write(
                CertUtils.content(publicKey), new FileOutputStream(new File("src/test/resources/rsa-public.key")));
        IOUtils.write(CertUtils.content(privKey), new FileOutputStream(new File("src/test/resources/rsa-private.key")));
    }

    @Test
    public void test_HmacAlgorithm() throws InterruptedException {
        String data = "Hello, world!";
        String key = "secret";

        HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, key.getBytes());
        byte[] result = hMac.digest(data.getBytes());

        String hmac = cn.hutool.core.codec.Base64.encode(result);
        // Hmac 算法是一种单向散列函数，它通常用于计算消息身份验证码 (MAC)。
        // 因此，在 HMAC 中不存在解密的操作，只能通过重新计算 MAC 值来验证消息的完整性和真实性。
        byte[] expected = hMac.digest(data.getBytes());
        byte[] actual = cn.hutool.core.codec.Base64.decode(hmac);
        Assertions.assertArrayEquals(expected, actual);
    }

    /**
     * 在Java中，可以使用 KeyPairGenerator 类的 getProvider() 方法来获取支持的密钥对算法列表。
     */
    @Test
    public void test_listAlgorithm() {
        Provider[] providers = Security.getProviders();

        for (Provider provider : providers) {
            System.out.println("Provider: " + provider.getName());
            for (String key : provider.stringPropertyNames()) {
                if (key.startsWith("KeyPairGenerator.")) {
                    String algorithm = key.substring("KeyPairGenerator.".length());
                    System.out.println("\tAlgorithm: " + algorithm);
                    for (String className : provider.getProperty(key).split("\\|")) {
                        try {
                            Class<?> clazz = Class.forName(className);
                            KeyPairGenerator kpg = (KeyPairGenerator) clazz.newInstance();
                            System.out.println("\t\tClass: " + className + "\tStrength: " + kpg.getAlgorithm());
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
}
