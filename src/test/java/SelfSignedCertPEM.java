import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class SelfSignedCertPEM {
    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Generate the key pair
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", BC);
        kpGen.initialize(2048);
        KeyPair pair = kpGen.generateKeyPair();
        PrivateKey privKey = pair.getPrivate();

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
                pair.getPublic());

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


        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(pair.getPublic());
        AuthorityKeyIdentifier authorityKeyIdentifier = new JcaX509ExtensionUtils().createAuthorityKeyIdentifier(pair.getPublic());

        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        certBuilder.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);


        // Sign the certificate
        ContentSigner sigGen = new JcaContentSignerBuilder("SHA256WithRSA").build(privKey);
        X509Certificate cert = new JcaX509CertificateConverter().setProvider(BC).getCertificate(certBuilder.build(sigGen));

//        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());
//        X509Certificate cert = new JcaX509CertificateConverter().getCertificate(certBuilder.build(signer));

        // Save the certificate and private key to files in PEM format
        StringWriter sw = new StringWriter();
        PEMWriter pemWriter = new PEMWriter(sw);
        pemWriter.writeObject(cert);
        pemWriter.flush();
        pemWriter.close();

        String certPem = sw.toString();
        System.out.println(certPem);

        FileOutputStream out = new FileOutputStream("cert.pem");
        out.write(certPem.getBytes());
        out.close();

        sw = new StringWriter();
        pemWriter = new PEMWriter(sw);
        pemWriter.writeObject(privKey);
        pemWriter.flush();
        pemWriter.close();

        String keyPem = sw.toString();
        System.out.println(keyPem);

        out = new FileOutputStream("key.pem");
        out.write(certPem.getBytes());
        out.close();
    }
}
