import com.dyrnq.utils.CertUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
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
import org.junit.jupiter.api.Test;

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

        //        secp256r1和prime256v1其实指的是同一种椭圆曲线，也称为NIST P-256或者简称P-256。这个标准定义了一条特定的椭圆曲线，用于椭圆曲线密码学中的加密和数字签名算法。
        //
        // "secp256r1"是该椭圆曲线在安全椭圆曲线规范（SEC）中的命名方式，其中的"256"表示该曲线的位长度为256位。而"prime256v1"则是在X9.62标准中对该曲线的命名方式，其中的"256"同样表示位长度。
        //        所以，secp256r1和prime256v1并没有实质的区别，只是命名方式不同，是同一条256位的椭圆曲线。它们被广泛应用于许多密码学协议和应用中，例如TLS/SSL通信、数字签名和密钥交换等。
        //        除了prime256v1（secp256r1或P-256）之外，还有许多其他的椭圆曲线曲线被广泛应用于密码学和安全通信中。以下是一些常见的曲线：
        //        secp384r1（P-384）：使用384位素数作为有限域上的模数，提供比prime256v1更高的安全性级别。
        //        secp521r1（P-521）：使用521位素数作为有限域上的模数，提供非常高的安全性级别。
        //        Curve25519：由Daniel J. Bernstein提出的一种高性能曲线，使用255位素数作为有限域上的模数。它在密钥交换协议中得到广泛应用。
        //        secp256k1：与prime256v1类似，但参数略有不同。它被用于比特币和其他某些加密货币的公钥和私钥生成。
        //        这只是其中一些常见的椭圆曲线，实际上还存在许多其他的曲线参数集。选择适当的曲线取决于具体的安全需求和性能考虑。在实际应用中，需要根据具体情况选择合适的曲线。

        //        在 "secp256r1" 中，"secp" 是 Secure Elliptic Curve（安全椭圆曲线）的缩写。它指的是该椭圆曲线所属的标准或规范。具体来说，"secp"
        // 是由美国国家标准与技术研究院（National Institute of Standards and Technology，简称 NIST）定义和推荐的一系列椭圆曲线标准。
        //        至于 "r1"，它是指在 "secp256r1" 中的特定曲线版本。这里的 "r1" 指的是第一个版本，表示在该曲线规范中的序号为 1。当存在多个相同长度的椭圆曲线时，通过不同的序号来区分它们。
        //        因此，"secp256r1" 表示 NIST 标准中定义的一个256位的椭圆曲线，其中 "secp" 表示安全椭圆曲线，"256" 表示位长度为256位，而 "r1" 则表示该曲线的第一个版本。

        // 选择 ECC 曲线
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("P-521");
        // ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime256v1");
        // 生成密钥对
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        keyGen.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        // 构建证书请求主题
        X500Name subject = new X500Name("CN=hello.com");

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
                keyPair.getPublic());

        // 添加密钥用法扩展
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        certBuilder.addExtension(
                org.bouncycastle.asn1.x509.Extension.keyUsage,
                true,
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));

        // 添加基本约束扩展
        certBuilder.addExtension(
                org.bouncycastle.asn1.x509.Extension.basicConstraints,
                true,
                new org.bouncycastle.asn1.x509.BasicConstraints(false));

        // 签署证书请求
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withECDSA").build(keyPair.getPrivate());

        X509CertificateHolder certificateHolder = certBuilder.build(signer);
        X509Certificate cert =
                new org.bouncycastle.jce.provider.X509CertificateObject(certificateHolder.toASN1Structure());

        // 将证书和私钥输出到文件
        IOUtils.write(
                CertUtils.content(cert), new FileOutputStream(new File("src/test/resources/ecc_certificate.pem")));
        IOUtils.write(
                CertUtils.content(keyPair.getPrivate()),
                new FileOutputStream(new File("src/test/resources/ecc_privatekey.pem")));
    }
}
