import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;

public class SelfSignedECC extends BaseJunit {

    /**
     * 此示例使用了 Bouncy Castle（BC）包，首先添加了 BC 提供商。
     * 然后，选择 ECC 曲线并生成密钥对。
     * 接着，构建证书请求主题和颁发者，并设置有效期。
     * 之后，使用 X509v3CertificateBuilder 构建证书请求，并添加密钥用法扩展和基本约束扩展。
     * 最后，使用 ContentSigner 对证书请求进行签名，生成证书，并将证书和私钥输出到文件。
     */
    @Test
    public void test_ECC() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // 选择 ECC 曲线
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");

        // 生成密钥对
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        keyGen.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        // 构建证书请求主题
        X500Name subject = new X500Name("CN=Your Name");

        // 构建证书颁发者
        X500Name issuer = subject;

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

        // 添加密钥用法扩展
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        certBuilder.addExtension(
                org.bouncycastle.asn1.x509.Extension.keyUsage,
                true,
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment)
        );


        // 添加基本约束扩展
        certBuilder.addExtension(
                org.bouncycastle.asn1.x509.Extension.basicConstraints,
                true,
                new org.bouncycastle.asn1.x509.BasicConstraints(false)
        );

        // 签署证书请求
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withECDSA").build(keyPair.getPrivate());

        X509CertificateHolder certificateHolder = certBuilder.build(signer);
        X509Certificate cert = new org.bouncycastle.jce.provider.X509CertificateObject(certificateHolder.toASN1Structure());

        // 将证书和私钥输出到文件
        IOUtils.write(CertUtils.content(cert), new FileOutputStream(new File("src/test/resources/ecc_certificate.pem")));
        IOUtils.write(CertUtils.content(keyPair.getPrivate()), new FileOutputStream(new File("src/test/resources/ecc_privatekey.pem")));


    }
}
