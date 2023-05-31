import com.dyrnq.apisix.domain.SSL;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESDecryptor extends BaseJunit {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    @Test
    public void test_decrypt() throws Exception {
        SSL ssl = client.getSSL("1");
        String encrypted = ssl.getKey();
        String key = "edd1c9f0985e76a2";
        String iv = "edd1c9f0985e76a2";
        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
        byte[] keyBytes = key.getBytes("UTF-8");
        byte[] ivBytes = iv.getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        System.out.println(new String(decryptedBytes));
    }
}
