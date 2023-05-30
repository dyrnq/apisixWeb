import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Test;

public class SelfSignedCAPEM {
    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    @Test
    public void test_createCA() throws NoSuchAlgorithmException, CertificateException, OperatorCreationException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        KeyPair caKeyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privKey = caKeyPair.getPrivate();
        PublicKey publicKey = caKeyPair.getPublic();

        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, "My CA");

        X500Name issuer = nameBuilder.build();

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption")
                .setProvider(BC).build(privKey);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer,
                BigInteger.valueOf(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000),
                new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000),
                issuer,
                publicKey);


        BasicConstraints basicConstraints = new BasicConstraints(true); // CA flag is true
        certBuilder.addExtension(new Extension(Extension.basicConstraints, true, basicConstraints.getEncoded()));
        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(publicKey);
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        KeyUsage keyUsage = new KeyUsage(KeyUsage.cRLSign | KeyUsage.keyCertSign);
        certBuilder.addExtension(Extension.keyUsage,true,keyUsage.getEncoded());

        X509CertificateHolder certificateHolder = certBuilder.build(signer);




        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter.setProvider(BC);

        X509Certificate caCert = certConverter.getCertificate(certificateHolder);


        CertUtils.pemWriter(caCert,privKey,"ca");
//        StringWriter sw = new StringWriter();
//        JcaPEMWriter pemWriter = new JcaPEMWriter(sw);
//        pemWriter.writeObject(caCert);
//        pemWriter.flush();
//        pemWriter.close();
//
//        String certPem = sw.toString();
//        System.out.println(certPem);
//
//        FileOutputStream out = new FileOutputStream("ca.crt");
//        out.write(certPem.getBytes());
//        out.close();
//
//
//        sw = new StringWriter();
//        pemWriter = new JcaPEMWriter(sw);
//        pemWriter.writeObject(privKey);
//        pemWriter.flush();
//        pemWriter.close();
//
//        String keyPem = sw.toString();
//        System.out.println(keyPem);
//
//        out = new FileOutputStream("ca.key");
//        out.write(keyPem.getBytes());
//        out.close();


    }
}

