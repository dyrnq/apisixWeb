import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class SelfSignedCAPEM {
    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Test
    public void test_createCA() throws NoSuchAlgorithmException, CertificateException, OperatorCreationException, IOException, NoSuchProviderException {
        // Generate the key pair
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", BC);
        kpGen.initialize(2048);

        KeyPair pair = kpGen.generateKeyPair();
        PrivateKey privKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.ST, "GD");
        nameBuilder.addRDN(BCStyle.C, "My CA");
        nameBuilder.addRDN(BCStyle.L, "SZ");
        nameBuilder.addRDN(BCStyle.O, "vihoo");
        nameBuilder.addRDN(BCStyle.OU, "dev");
        nameBuilder.addRDN(BCStyle.CN, "test.com");
        nameBuilder.addRDN(BCStyle.EmailAddress, "yy@vivo.com");

        // Build the X500Name object
        X500Name issuer = nameBuilder.build();


        Date notBefore = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24); // 证书有效期从一天前开始
        Date notAfter = new Date(notBefore.getTime() + 3650L * 24L * 60L * 60L * 1000L); // 10 years validity
        // Create the certificate
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer,
                BigInteger.valueOf(System.currentTimeMillis()),
                notBefore,
                notAfter,
                issuer,
                publicKey);


        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true)); // CA flag is true
        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(publicKey);
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        KeyUsage keyUsage = new KeyUsage(KeyUsage.cRLSign | KeyUsage.keyCertSign);
        certBuilder.addExtension(Extension.keyUsage, true, keyUsage.getEncoded());


        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider(BC).build(privKey);
        X509CertificateHolder certificateHolder = certBuilder.build(signer);

        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter.setProvider(BC);

        X509Certificate caCert = certConverter.getCertificate(certificateHolder);
        IOUtils.write(CertUtils.content(caCert), Files.newOutputStream(new File("src/test/resources/ca.crt").toPath()));
        IOUtils.write(CertUtils.content(privKey), Files.newOutputStream(new File("src/test/resources/ca.key").toPath()));
    }
}

