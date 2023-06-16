import com.dyrnq.utils.CertUtils;
import com.dyrnq.utils.X509Holder;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertUtilsTest {


    public static void processShell(String command) throws IOException, InterruptedException {
//        String[] command = {"ls", "-l","src/test/resources"};
//        command = StringUtils.splitByWholeSeparator(" ",cmd);
//        System.out.println(JSONUtil.toJsonStr(command));
//        ProcessBuilder processBuilder = new ProcessBuilder(command);
//        Process process = processBuilder.start();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//        String line = null;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//
//        int exitCode = process.waitFor();
//        System.out.println("\nExited with error code : " + exitCode);

// 执行shell命令
        Process process = Runtime.getRuntime().exec(command);

// 读取命令的输出结果
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        InputStream errorStream = process.getErrorStream();
        BufferedReader readerError = new BufferedReader(new InputStreamReader(errorStream));
        String lineError;
        while ((lineError = readerError.readLine()) != null) {
            System.out.println(lineError);
        }


// 等待命令执行完毕并检查返回值
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Command executed successfully.");
        } else {
            System.out.println("Command failed with exit code: " + exitCode);
        }

    }

    @Test
    public void test_readCert() throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream("src/test/resources/server.crt");
        java.security.cert.Certificate c = cf.generateCertificate(in);
        X509Certificate t = (X509Certificate) c;
        System.out.println(t.getVersion());
        System.out.println(t.getSerialNumber().toString(16));
        System.out.println(t.getSubjectDN());
        System.out.println(t.getIssuerDN());
        System.out.println(t.getNotBefore());
        System.out.println(t.getNotAfter());
        System.out.println(t.getSigAlgName());
        byte[] sig = t.getSignature();
        System.out.println(new BigInteger(sig).toString(16));
        PublicKey pk = t.getPublicKey();
        byte[] pkenc = pk.getEncoded();
        for (int i = 0; i < pkenc.length; i++) {
            System.out.print(pkenc[i] + ",");
        }
        in.close();
    }

    @Test
    public void test_loadPrivateKey() throws Exception {
        PrivateKey privateKey = CertUtils.load(new File("src/test/resources/server.key"));
        String contentPrivateKey = CertUtils.content(privateKey);
        System.out.println(contentPrivateKey);

        PrivateKey privateKey_CA = CertUtils.load(new File("src/test/resources/example-ca.key"));
        String contentPrivateKey_CA = CertUtils.content(privateKey_CA);
        System.out.println(contentPrivateKey_CA);
    }

    @Test
    public void test_loadCertificate() throws Exception {
        X509Certificate cert = CertUtils.loadCertificate(new File("src/test/resources/server.crt"));
        String contentCert = CertUtils.content(cert);
        System.out.println(contentCert);
    }

    @Test
    public void test_genCA() throws Exception {
        X509Holder holder = CertUtils.genCA("CN=My CN", 365 * 2 * 100);
        IOUtils.write(holder.getCert(), new FileOutputStream(new File("src/test/resources/example-ca.crt")));
        IOUtils.write(holder.getKey(), new FileOutputStream(new File("src/test/resources/example-ca.key")));

        processShell("openssl x509 -in src/test/resources/example-ca.crt -text -noout");
    }

    @Test
    public void test_gen() throws Exception {
        X509Holder holderDomain = CertUtils.gen("C = CN, ST = GD, L = SZ, O = vihoo, OU = dev, CN = hello.com, emailAddress = yy@vivo.com", new String[]{"hello.com", "www.hello.com", "127.0.0.1", "192.168.100.22"});
        IOUtils.write(holderDomain.getCert(), new FileOutputStream(new File("src/test/resources/example-domain.crt")));
        IOUtils.write(holderDomain.getKey(), new FileOutputStream(new File("src/test/resources/example-domain.key")));


        processShell("openssl x509 -in src/test/resources/example-domain.crt -text -noout");
        processShell("openssl x509 -in src/test/resources/server.crt -text -noout");
    }

    @Test
    public void test_genByCa() throws Exception {
        X509Certificate caCert = CertUtils.loadCertificate(new File("src/test/resources/example-ca.crt"));
        PrivateKey caKey = CertUtils.load(new File("src/test/resources/example-ca.key"));
        X509Holder domain = CertUtils.gen("C = CN, ST = GD, L = SZ, O = vihoo, OU = dev, CN = hello.com, emailAddress = yy@vivo.com", new String[]{"hello.com", "www.hello.com", "127.0.0.1", "192.168.100.22"}, caCert, caKey);
        IOUtils.write(domain.getCert(), new FileOutputStream(new File("src/test/resources/example-domain-by-ca.crt")));
        IOUtils.write(domain.getKey(), new FileOutputStream(new File("src/test/resources/example-domain-by-ca.key")));
        processShell("openssl x509 -in src/test/resources/example-domain-by-ca.crt -text -noout");
    }

    @Test
    public void test_renew() throws Exception {
        test_genCA();
        test_genByCa();
        test_gen();
    }

    @Test
    public void test_convert() throws Exception {
        PrivateKey privateKey_CA = CertUtils.load(new File("src/test/resources/example-ca.key"));
        CertUtils.toPKCS8(privateKey_CA);
    }

    /**
     * 模拟OpenSSL的x509 -in dsa-cert.pem -noout -text命令的输出。
     * @throws Exception
     */
    @Test
    public void test_print() throws Exception {
        FileInputStream certFileInputStream = new FileInputStream(new File("src/test/resources/example-ca.crt"));
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(certFileInputStream);
        System.out.println(ASN1Dump.dumpAsString(new ASN1InputStream(x509Certificate.getEncoded()).readObject()));
    }


}
