package com.dyrnq.utils;

import com.google.common.net.InetAddresses;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
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

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertUtils {

    private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
            } else if (obj instanceof PrivateKeyInfo){
                return new JcaPEMKeyConverter().setProvider(BC).getPrivateKey((PrivateKeyInfo) obj);
            }else {
                throw new IllegalArgumentException("Unsupported PEM object.");
            }
        } finally {
            IOUtils.closeQuietly(reader);
        }

    }

    public static PrivateKey load(String in) throws IOException, CertificateException {
        Reader reader = null;
        try {
            reader = new StringReader((String) in);


            PEMParser parser = new PEMParser(reader);
            Object obj = parser.readObject();
            if (obj instanceof PEMKeyPair) {
                // 将 PEM 密钥对转换为 JCE 格式的密钥对
                KeyPair keyPair = new JcaPEMKeyConverter().setProvider(BC).getKeyPair((PEMKeyPair) obj);
                return keyPair.getPrivate();
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

    public static String content(X509Certificate i) throws IOException {
        return pemWriter(i);
    }

    public static X509Holder gen(String subjectDN) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException {
        return gen(subjectDN,false,365*100,4096,null);
    }

    public static X509Holder gen(String subjectDN,String[] sni) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException {
        return gen(subjectDN,false,365*100,4096,sni);
    }

    public static X509Holder genCA(String subjectDN) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException {
        return gen(subjectDN,true,365*100,4096,null);
    }
    public static X509Holder genCA(String subjectDN,int days) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException {
        return gen(subjectDN,true,days,4096,null);
    }


    public static X509Holder gen(String subjectDN,String[] sni,X509Certificate issuerCA, PrivateKey issuerCAKey) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException {
        return gen(subjectDN,false,365*100,4096,sni,issuerCA,issuerCAKey);
    }

    public static X509Holder gen(
            String subjectDN,
            boolean isCA,
            int days,
            int keySize,
            String[] sni) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException {
        return gen(subjectDN, isCA, days, keySize, sni, null, null);
    }

    public static X509Holder gen(
            String subjectDN,
            boolean isCA,
            int days,
            int keySize,
            String[] sni,
            X509Certificate issuerCA, PrivateKey issuerCAKey) throws NoSuchAlgorithmException, IOException, CertificateException, OperatorCreationException {
        X509Holder holder = new X509Holder();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);

        KeyPair caKeyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privKey = caKeyPair.getPrivate();
        PublicKey publicKey = caKeyPair.getPublic();

        X500Name issuer = new X500Name(subjectDN);


        Date notBefore = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24);
        Date notAfter = new Date(notBefore.getTime() + days * 24L * 60L * 60L * 1000L);

        ContentSigner signer;
        X509v3CertificateBuilder certBuilder;

        if(issuerCA==null) {
            signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider(BC).build(privKey);
            certBuilder = new JcaX509v3CertificateBuilder(
                    issuer,
                    BigInteger.valueOf(System.currentTimeMillis()),
                    notBefore,
                    notAfter,
                    issuer,
                    publicKey);
        }else{
            // 构造 X.509 证书请求
            PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                    new X500Name(subjectDN), // 填写主题名称
                    publicKey
            );
//            JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
            signer = new JcaContentSignerBuilder("SHA256withRSA").setProvider(BC).build(issuerCAKey);
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

        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(issuerCA!=null?false:true)); // CA flag is true
        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(publicKey);
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        KeyUsage keyUsage = new KeyUsage(KeyUsage.cRLSign | KeyUsage.keyCertSign);
        certBuilder.addExtension(Extension.keyUsage, true, keyUsage.getEncoded());

        if (sni != null && sni.length>0){
            // Add additional domain names as Subject Alternative Names (SANs)
            List<GeneralName> sanList = new ArrayList<>();
                for(String i : sni){
                    if (InetAddresses.isInetAddress(i)){
                        sanList.add(new GeneralName(GeneralName.iPAddress, i));
                    }else{
                        sanList.add(new GeneralName(GeneralName.dNSName, i));
                    }
                }
            GeneralNames subjectAltNames = new GeneralNames(sanList.toArray(new GeneralName[0]));
            certBuilder.addExtension(Extension.subjectAlternativeName, false, subjectAltNames);
        }

        X509CertificateHolder certificateHolder = certBuilder.build(signer);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter.setProvider(BC);

        X509Certificate caCert = certConverter.getCertificate(certificateHolder);

        holder.setCert(CertUtils.content(caCert));
        holder.setKey(CertUtils.content(privKey));

        return holder;
    }





}
