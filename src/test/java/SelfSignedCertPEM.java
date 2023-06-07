import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dyrnq.utils.CertUtils;
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
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Test;

public class SelfSignedCertPEM {
    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    @Test
    public void test_createCert() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Generate the key pair
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", BC);
        kpGen.initialize(2048);
        KeyPair pair = kpGen.generateKeyPair();
        PrivateKey privKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        // Set the DN information
        //X500Name dnName = new X500Name("CN=test.com");
//        X500Name issuer = new X500Name("CN=My Issuer");
//        X500Name subject = new X500Name("CN=test.com");

        // Set the state and country fields
        // /C=CN/ST=GD/L=SZ/O=vihoo/OU=dev/CN=reg.domain.com/emailAddress=yy@vivo.com
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.ST, "GD");
        builder.addRDN(BCStyle.C, "CN");
        builder.addRDN(BCStyle.L, "SZ");
        builder.addRDN(BCStyle.O,"vihoo");
        builder.addRDN(BCStyle.OU,"dev");
        builder.addRDN(BCStyle.CN,"test.com");
        builder.addRDN(BCStyle.EmailAddress,"yy@vivo.com");

        // Build the X500Name object
        X500Name subject = builder.build();


        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3650L * 24L * 60L * 60L * 1000L); // 10 years validity
        // Create the certificate
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                subject,
                new BigInteger(64, new SecureRandom()),
                now,
                expiryDate,
                subject,
                publicKey);

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
        AuthorityKeyIdentifier authorityKeyIdentifier = new JcaX509ExtensionUtils().createAuthorityKeyIdentifier(publicKey);

        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        certBuilder.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);


        // Sign the certificate
        ContentSigner sigGen = new JcaContentSignerBuilder("SHA256WithRSA").build(privKey);
        X509CertificateHolder certificateHolder = certBuilder.build(sigGen);
        X509Certificate cert = new JcaX509CertificateConverter().setProvider(BC).getCertificate(certificateHolder);

        IOUtils.write(CertUtils.content(cert),new FileOutputStream(new File("src/test/resources/example.crt")));
        IOUtils.write(CertUtils.content(privKey),new FileOutputStream(new File("src/test/resources/example.key")));
    }
}
