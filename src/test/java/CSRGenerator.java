import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.*;

public class CSRGenerator {

    @Test
    public void test_genCSR() throws NoSuchAlgorithmException, NoSuchProviderException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());

        // 生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 创建CSR
        X500Name x500Name = new X500Name("CN=Your Name, OU=Your Organizational Unit, O=Your Organization, L=Your City, ST=Your State, C=Your Country");
        PKCS10CertificationRequestBuilder csrBuilder = new JcaPKCS10CertificationRequestBuilder(x500Name, keyPair.getPublic());
        JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner contentSigner = signerBuilder.build(keyPair.getPrivate());
        PKCS10CertificationRequest csr = csrBuilder.build(contentSigner);

        // 保存CSR到文件
        FileOutputStream fos = new FileOutputStream("csr.csr");
        fos.write(csr.getEncoded());
        fos.close();


        // 保存CSR到文件为PEM格式，此转化等同于 openssl req -inform der -in csr.csr -outform pem -out mycsr.pem 这个命令
        StringWriter stringWriter = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter);
        pemWriter.writeObject(csr);
        pemWriter.close();
        String pemString = stringWriter.toString();
        FileWriter fileWriter = new FileWriter("csr.csr.PEM");
        fileWriter.write(pemString);
        fileWriter.close();
        // 可以使用 https://myssl.com/csr_decode.html 在线解析csr文件


    }
}
