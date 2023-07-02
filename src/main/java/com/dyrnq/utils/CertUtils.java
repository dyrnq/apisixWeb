package com.dyrnq.utils;

import com.google.common.net.InetAddresses;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

public class CertUtils {

    private static final int DEFAULT_KEY_SIZE = 2048;

    private static final int ECC_DEFAULT_KEY_SIZE = 256;

    private static final int DEFAULT_DAYS = 100 * 365;

    private static final String EncryRSA = "RSA";
    private static final String EncryECC = "ECC";


    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static String[] extractSNI(X509Certificate x509Cert) throws InvalidNameException, CertificateParsingException {
        Map<String, String> sniMap = _extractSNI(x509Cert);
        List<String> sni = new ArrayList<>();
        for (Map.Entry<String, String> entry : sniMap.entrySet()) {
            String key = entry.getKey();
            sni.add(key);
        }
        return sni.toArray(new String[sni.size()]);
    }

    private static Map<String, String> _extractSNI(X509Certificate x509Cert) throws InvalidNameException, CertificateParsingException {

        Map<String, String> sniMap = new HashMap<>();
        String subjectName = x509Cert.getSubjectX500Principal().getName();
        LdapName ldapName = new LdapName(subjectName);
        String cnValue = null;
        for (Rdn rdn : ldapName.getRdns()) {
            if (rdn.getType().equalsIgnoreCase("CN")) {
                cnValue = rdn.getValue().toString();
                // Do something with the CN value
                break;
            }
        }
        if (StringUtils.isNotBlank(cnValue)) {
            sniMap.put(cnValue, cnValue);
        }
        Collection<List<?>> altNames = x509Cert.getSubjectAlternativeNames();
        if (altNames != null) {
            for (List<?> altName : altNames) {
                if (altName.get(1) != null) {
                    String altNameStr = String.valueOf(altName.get(1));
                    sniMap.put(altNameStr, altNameStr);
                }
            }
        }
        return sniMap;
    }

    public static X509Certificate loadCertificate(String content) throws IOException, CertificateException {
        InputStream in = IOUtils.toInputStream(content, "UTF-8");
        return loadCertificate(in);
    }

    public static X509Certificate loadCertificate(InputStream in) throws IOException, CertificateException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate) cf.generateCertificate(in);
    }

    public static X509Certificate loadCertificate(File file) throws IOException, CertificateException {
        try (InputStream in = new FileInputStream(file)) {
            return loadCertificate(in);
        }
    }

    public static PrivateKey load(File file) throws IOException, CertificateException {
        Reader reader = null;

        try {
            reader = new FileReader(file);
            PEMParser parser = new PEMParser(reader);
            Object obj = parser.readObject();
            if (obj instanceof PEMKeyPair) {
                // 将 PEM 密钥对转换为 JCE 格式的密钥对
                KeyPair keyPair = new JcaPEMKeyConverter().setProvider(BC).getKeyPair((PEMKeyPair) obj);
                return keyPair.getPrivate();
            } else if (obj instanceof PrivateKeyInfo) {
                return new JcaPEMKeyConverter().setProvider(BC).getPrivateKey((PrivateKeyInfo) obj);
            } else {
                throw new IllegalArgumentException("Unsupported PEM object.");
            }
        } finally {
            IOUtils.closeQuietly(reader);
        }

    }

    public static PrivateKey load(String in) throws IOException, CertificateException {
        Reader reader = null;
        try {
            reader = new StringReader(in);


            PEMParser parser = new PEMParser(reader);
            Object obj = parser.readObject();
            if (obj instanceof PEMKeyPair) {
                // 将 PEM 密钥对转换为 JCE 格式的密钥对
                KeyPair keyPair = new JcaPEMKeyConverter().setProvider(BC).getKeyPair((PEMKeyPair) obj);
                return keyPair.getPrivate();
            } else if (obj instanceof PrivateKeyInfo) {
                return new JcaPEMKeyConverter().setProvider(BC).getPrivateKey((PrivateKeyInfo) obj);
            } else {
                throw new IllegalArgumentException("Unsupported PEM object.");
            }
        } finally {
            IOUtils.closeQuietly(reader);
        }

    }


    public static PrivateKey load(InputStream in) throws IOException, CertificateException {
        Reader reader = null;
        try {
            reader = new InputStreamReader(in);
            PEMParser parser = new PEMParser(reader);
            Object obj = parser.readObject();
            if (obj instanceof PEMKeyPair) {
                // 将 PEM 密钥对转换为 JCE 格式的密钥对
                KeyPair keyPair = new JcaPEMKeyConverter().setProvider(BC).getKeyPair((PEMKeyPair) obj);
                return keyPair.getPrivate();
            } else if (obj instanceof PrivateKeyInfo) {
                return new JcaPEMKeyConverter().setProvider(BC).getPrivateKey((PrivateKeyInfo) obj);
            } else {
                throw new IllegalArgumentException("Unsupported PEM object.");
            }
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }


    private static String pemWriter(Object item) throws IOException {
        StringWriter sw = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(sw);
        pemWriter.writeObject(item);
        pemWriter.flush();
        pemWriter.close();
        String certPem = sw.toString();
        return certPem;
    }

    public static String content(PrivateKey i) throws IOException {
        return pemWriter(i);
    }

    public static String content(PublicKey i) throws IOException {
        return pemWriter(i);
    }

    public static String content(X509Certificate i) throws IOException {
        return pemWriter(i);
    }

    public static X509Holder genRSA(String subjectDN) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryRSA, subjectDN, false, DEFAULT_DAYS, DEFAULT_KEY_SIZE, null, null, null);
    }

    public static X509Holder genRSA(String subjectDN, String[] sni) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryRSA, subjectDN, false, DEFAULT_DAYS, DEFAULT_KEY_SIZE, sni, null, null);
    }

    public static X509Holder genRSA(String subjectDN, String[] sni, int keySize) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryRSA, subjectDN, false, DEFAULT_DAYS, keySize, sni, null, null);
    }


    public static X509Holder genRSA(String subjectDN, String[] sni, X509Certificate issuerCA, PrivateKey issuerCAKey) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryRSA, subjectDN, true, DEFAULT_DAYS, DEFAULT_KEY_SIZE, sni, issuerCA, issuerCAKey);
    }


    public static X509Holder genECC(String subjectDN) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryECC, subjectDN, false, DEFAULT_DAYS, ECC_DEFAULT_KEY_SIZE, null, null, null);
    }

    public static X509Holder genECC(String subjectDN, String[] sni) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryECC, subjectDN, false, DEFAULT_DAYS, ECC_DEFAULT_KEY_SIZE, sni, null, null);
    }

    public static X509Holder genECC(String subjectDN, String[] sni, int keySize) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryECC, subjectDN, false, DEFAULT_DAYS, keySize, sni, null, null);
    }

    public static X509Holder genECC(String subjectDN, String[] sni, X509Certificate issuerCA, PrivateKey issuerCAKey) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryECC, subjectDN, true, DEFAULT_DAYS, ECC_DEFAULT_KEY_SIZE, sni, issuerCA, issuerCAKey);
    }


    public static X509Holder genCA(String subjectDN) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryRSA, subjectDN, true, DEFAULT_DAYS, DEFAULT_KEY_SIZE, null, null, null);
    }

    public static X509Holder genCA(String subjectDN, int days) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryRSA, subjectDN, true, days, DEFAULT_KEY_SIZE, null, null, null);
    }

    public static X509Holder genCAuseECC(String subjectDN) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryECC, subjectDN, true, DEFAULT_DAYS, ECC_DEFAULT_KEY_SIZE, null, null, null);
    }

    public static X509Holder genCAuseECC(String subjectDN, int days) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        return gen(EncryECC, subjectDN, true, days, ECC_DEFAULT_KEY_SIZE, null, null, null);
    }

    private static X509Holder gen(
            String algorithm,
            String subjectDN,
            boolean isCA,
            int days,
            int keySize,
            String[] sni,
            X509Certificate issuerCA, PrivateKey issuerCAKey) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException, InvalidAlgorithmParameterException {
        X509Holder holder = new X509Holder();

        KeyPairGenerator keyPairGenerator = null;

        if (StringUtils.isBlank(algorithm) || StringUtils.equalsIgnoreCase("RSA", algorithm)) {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
        } else if (StringUtils.equalsIgnoreCase("ECC", algorithm)) {
            try {
                keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            } catch (NoSuchProviderException e) {
                throw new RuntimeException(e);
            }
            // 选择 ECC 曲线
            ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("P-" + keySize);
            // 生成密钥对
            keyPairGenerator.initialize(ecSpec, new SecureRandom());
        }
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        X500Name issuer = new X500Name(subjectDN);


        Date notBefore = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24);
        Date notAfter = new Date(notBefore.getTime() + days * 24L * 60L * 60L * 1000L);

        ContentSigner signer;
        X509v3CertificateBuilder certBuilder;

        if (issuerCA == null) {
//            if (StringUtils.isBlank(algorithm) || StringUtils.equalsIgnoreCase("RSA", algorithm)) {
//                signer = new JcaContentSignerBuilder("SHA256withRSA").setProvider(BC).build(privKey);
//            } else {
//                signer = new JcaContentSignerBuilder("SHA256withECDSA").setProvider(BC).build(privKey);
//            }
            if (StringUtils.equalsIgnoreCase("EC", privKey.getAlgorithm())) {
                signer = new JcaContentSignerBuilder("SHA256with" + privKey.getAlgorithm() + "DSA").setProvider(BC).build(privKey);
            } else {
                signer = new JcaContentSignerBuilder("SHA256with" + privKey.getAlgorithm()).setProvider(BC).build(privKey);
            }
            certBuilder = new JcaX509v3CertificateBuilder(
                    issuer,
                    BigInteger.valueOf(System.currentTimeMillis()),
                    notBefore,
                    notAfter,
                    issuer,
                    publicKey);
        } else {
            // 构造 X.509 证书请求
            PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                    new X500Name(subjectDN), // 填写主题名称
                    publicKey
            );
//            JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
            signer = new JcaContentSignerBuilder("SHA256with" + issuerCAKey.getAlgorithm()).setProvider(BC).build(issuerCAKey);
            PKCS10CertificationRequest csr = p10Builder.build(signer);

            // 根据证书请求生成证书
            certBuilder = new JcaX509v3CertificateBuilder(
                    issuerCA, // 使用 CA 的证书颁发新的证书
                    new BigInteger(64, new SecureRandom()), // 生成随机序列号
                    notBefore,
                    notAfter,
                    csr.getSubject(), // 使用证书请求中的主题名称
                    publicKey // 使用证书请求中的公钥
            );
        }

        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(issuerCA == null)); // CA flag is true
        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(publicKey);
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        KeyUsage keyUsage = new KeyUsage(KeyUsage.cRLSign | KeyUsage.keyCertSign);
        certBuilder.addExtension(Extension.keyUsage, true, keyUsage.getEncoded());

        if (sni != null && sni.length > 0) {
            // Add additional domain names as Subject Alternative Names (SANs)
            List<GeneralName> sanList = new ArrayList<>();
            for (String i : sni) {
                if (InetAddresses.isInetAddress(i)) {
                    sanList.add(new GeneralName(GeneralName.iPAddress, i));
                } else {
                    sanList.add(new GeneralName(GeneralName.dNSName, i));
                }
            }
            GeneralNames subjectAltNames = new GeneralNames(sanList.toArray(new GeneralName[0]));
            certBuilder.addExtension(Extension.subjectAlternativeName, false, subjectAltNames);
        }

        X509CertificateHolder certificateHolder = certBuilder.build(signer);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter.setProvider(BC);

        X509Certificate certificate = certConverter.getCertificate(certificateHolder);

//        //将PKCS#1私钥转换为PKCS#8格式
//        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(converter.getPrivateKey((PrivateKey) privKey).getEncoded());
//        PrivateKey privateKey = new JcaPEMKeyConverter().setProvider("BC").getPrivateKey(pkInfo);
//
//        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//        PKCS8EncodedKeySpec pkcs8Spec = converter.getPrivateKey(privKey);
//        PrivateKey privateKey = KeyFactory.getInstance("RSA", "BC").generatePrivate(pkcs8Spec);


        holder.setCert(CertUtils.content(certificate));
        holder.setKey(CertUtils.content(privKey));
        holder.setCertificate(certificate);
        holder.setKeyPair(keyPair);
        return holder;
    }


//    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
//    PrivateKeyInfo pki = PrivateKeyInfo.getInstance(spec.getEncoded());
//    PemObject pemObject = new PemObject("PRIVATE KEY", pki.toASN1Primitive().getEncoded());


    public static String toPKCS8(PrivateKey pkcs1PrivateKey) throws NoSuchAlgorithmException, IOException {
        // Get the encoded bytes of the private key
        byte[] encodedPrivateKey = pkcs1PrivateKey.getEncoded();

        // Convert the key to PKCS#8 format
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);

        // Get an instance of a key factory that can parse PKCS#8 encoded keys
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Parse the PKCS#8 encoded key
        PrivateKey pkcs8PrivateKey = null;
        try {
            pkcs8PrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

//        byte[] pkcs8Bytes = pkcs8PrivateKey.getEncoded();
//        String pkcs8String = Base64.getEncoder().encodeToString(pkcs8Bytes);
//        PrivateKeyInfo pki = PrivateKeyInfo.getInstance(pkcs8EncodedKeySpec.getEncoded());
//        PemObject pemObject = new PemObject("PRIVATE KEY", pki.toASN1Primitive().getEncoded());

        PemObject pemObject = new PemObject("PRIVATE KEY", pkcs8PrivateKey.getEncoded());
        StringWriter stringWriter = new StringWriter();
        try (PemWriter pemWriter = new JcaPEMWriter(stringWriter)) {
            pemWriter.writeObject(pemObject);
        }
        //byte[] pkcs8PrivateKeyBytes = stringWriter.toString().getBytes();

        return stringWriter.toString();

        // Now we can get the Base64 encoded string of the PKCS#8 key
//        byte[] pkcs8Bytes = pkcs8PrivateKey.getEncoded();
//        String pkcs8String = Base64.getEncoder().encodeToString(pkcs8Bytes);
//        return pkcs8String;
    }

    public static KeyPair createECKeyPair() {
//        除了secp256r1和prime256v1之外，还有许多其他的椭圆曲线加密算法可供选择，例如：
//
//        secp384r1/prime384v1：使用384位的椭圆曲线参数。
//        secp521r1/prime521v1：使用521位的椭圆曲线参数。
//        Curve25519：一种基于扭曲爱德华兹曲线的加密算法，参数长度为255位，被广泛应用于加密协议中。
        return createECKeyPair("secp384r1");
    }

    public static KeyPair createECKeyPair(String ecSpecName) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
            ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(ecSpecName);
            keyGen.initialize(ecSpec, new SecureRandom());
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException ex) {
            throw new IllegalStateException(ex);
        }
    }


    // Generate Ed25519 key pair
    public static AsymmetricCipherKeyPair generateEd25519KeyPair() {
        Ed25519KeyPairGenerator generator = new Ed25519KeyPairGenerator();
        generator.init(new Ed25519KeyGenerationParameters(null));
        return generator.generateKeyPair();
    }
}
