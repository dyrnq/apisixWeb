import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.jupiter.api.Test;
import org.shredzone.acme4j.util.KeyPairUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Date;

public class PKCS12Example {
    public static final String DUMMY_CN = "CN=letsencrypt-java-helper";

    Instant getNow() {
        return Instant.now();
    }
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    private Certificate selfSign(KeyPair keyPair, Instant notBefore, Instant notAfter) {
        X500Name dnName = new X500Name(DUMMY_CN);
        BigInteger certSerialNumber = BigInteger.valueOf(getNow().toEpochMilli());
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                dnName,
                certSerialNumber,
                Date.from(notBefore),
                Date.from(notAfter),
                dnName,
                subjectPublicKeyInfo
        );
        try {
            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").
                    setProvider(BouncyCastleProvider.PROVIDER_NAME).build(keyPair.getPrivate());
            X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);
            return new JcaX509CertificateConverter().getCertificate(certificateHolder);
        } catch (CertificateException | OperatorCreationException ex) {
            throw new RuntimeException(ex);
        }

    }


    private KeyStore createBasicKeystoreWithSelfSignedCert() {
        try {
            int keySize = 2048;

            Period period = Period.ofYears(100);
            // 将 Period 转换为 Duration
            Duration accountCertValidity = Duration.ZERO.plusDays(period.getDays());

            String keyAlias = "tomcat";
            String accountKeyAlias = "letsencrypt-user";

            KeyPair accountKey = KeyPairUtils.createKeyPair(keySize);
            KeyPair domainKey = KeyPairUtils.createKeyPair(keySize);

            java.security.KeyStore newKeystore = KeyStore.getInstance("PKCS12");
            newKeystore.load(null, null);


//            Instant startTime = Instant.parse("2000-06-15T12:00:00Z");
//            Instant endTime = Instant.parse("2100-06-16T14:30:00Z");
//
//            Duration accountCertValidity = TemporalUtil.between(startTime,endTime);


            Certificate signedAccount = selfSign(accountKey, getNow(), getNow().plus(accountCertValidity));
            Instant epoch = Instant.parse("1970-01-01T00:00:00Z");
            Certificate signedDomain = selfSign(domainKey, epoch, epoch);
            newKeystore.setKeyEntry(keyAlias, domainKey.getPrivate(), keyPassword().toCharArray(), new Certificate[]{signedDomain});
            newKeystore.setKeyEntry(accountKeyAlias, accountKey.getPrivate(), keyPassword().toCharArray(), new Certificate[]{signedAccount});
            return newKeystore;
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String keyPassword() {
        return "change-me";
    }

    private String getKeyStorePassword() {
        return "change-me";
    }

    @Test
    public void test_createBasicKeystoreWithSelfSignedCert() {
        KeyStore keystore = createBasicKeystoreWithSelfSignedCert();
        File keystoreFile = new File("src/test/resources/tomcat.pkcs12");
        try (OutputStream os = Files.newOutputStream(keystoreFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            keystore.store(os, getKeyStorePassword().toCharArray());
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
