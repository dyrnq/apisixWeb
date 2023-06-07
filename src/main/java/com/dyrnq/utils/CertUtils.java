package com.dyrnq.utils;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

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

}
