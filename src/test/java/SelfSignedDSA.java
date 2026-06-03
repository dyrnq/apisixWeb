import com.dyrnq.utils.CertUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.jupiter.api.Test;

public class SelfSignedDSA {

    @Test
    public void test_dsa() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Generate a key pair for the certificate
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "BC");

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);

        //        DSAParameterSpec dsaParamSpec = new DSAParameterSpec(new
        // BigInteger("F5EDBCE8A0C0B7D3E2F39A5EE86BEFE1F7C38EA321AF9AFAFD3904699B7D5E3DAD6ABBB8B5AD63761A5B62AB365BAF1F9B5EDE28F1C4AC3CD975F4FC53B9D31E", 16), new BigInteger("884119A574E5A8DAC7747F9447B55F9FD96EBDDF", 16), new BigInteger("E01DE54C3D52C6B96D78CBFDC681C80AECFED2578AE6A996D59C642EF30246A72C6B6DCA2F0B44A9907BF8CFD4F542D1C5CADF9D832C21A73A1B7825D27F301528FF4B8A509EB3D201B5A8DD1B5"));
        //        keyGen.initialize(dsaParamSpec);

        KeyPair keyPair = keyGen.generateKeyPair();

        // Set the DN of the certificate issuer
        X500Name issuer = new X500Name("CN=My CA");

        // Set the DN of the certificate subject
        X500Name subject = new X500Name("CN=example.com, OU=IT, O=Example Inc., L=Los Angeles, ST=California, C=US");

        // Set the start and end dates of the certificate
        Date startDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        Date endDate = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000);

        // Generate a certificate using the key pair and the issuer and subject DNs
        JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                issuer, BigInteger.valueOf(1), startDate, endDate, subject, keyPair.getPublic());
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withDSA").build(keyPair.getPrivate());
        X509CertificateHolder certificateHolder = certificateBuilder.build(signer);
        JcaX509CertificateConverter certificateConverter = new JcaX509CertificateConverter();
        certificateConverter.setProvider("BC");
        java.security.cert.X509Certificate certificate = certificateConverter.getCertificate(certificateHolder);

        // Save the certificate and private key to disk
        File certFile = new File("src/test/resources/dsa-cert.pem");
        File keyFile = new File("src/test/resources/dsa-key.pem");

        // 将证书和私钥输出到文件
        IOUtils.write(CertUtils.content(certificate), new FileOutputStream(certFile));
        IOUtils.write(CertUtils.content(keyPair.getPrivate()), new FileOutputStream(keyFile));
    }
}
