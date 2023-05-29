import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class CertUtils {

    public static void pemWriter(Object cert,Object privKey,String namePrefix) throws IOException {

        StringWriter sw = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(sw);
        pemWriter.writeObject(cert);
        pemWriter.flush();
        pemWriter.close();

        String certPem = sw.toString();
        System.out.println(certPem);

        FileOutputStream out = new FileOutputStream(namePrefix+".crt");
        out.write(certPem.getBytes());
        out.close();


        sw = new StringWriter();
        pemWriter = new JcaPEMWriter(sw);
        pemWriter.writeObject(privKey);
        pemWriter.flush();
        pemWriter.close();

        String keyPem = sw.toString();
        System.out.println(keyPem);

        out = new FileOutputStream(namePrefix+".key");
        out.write(keyPem.getBytes());
        out.close();
    }
}
