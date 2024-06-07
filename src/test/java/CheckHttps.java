import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public class CheckHttps {
    @Test
    public void test_checkHttpsCert() throws IOException, CertificateEncodingException {

        URL url = new URL("https://dyrnq.com");

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier((string, ssls) -> true);
        conn.setSSLSocketFactory(prepareContext().getSocketFactory());

        conn.connect();
        // 获取证书链

        Certificate[] certificates = conn.getServerCertificates();
        conn.disconnect();

        // 打印证书信息
        for (java.security.cert.Certificate cert : certificates) {
            //System.out.println(cert);
            System.out.println(cert);
        }

    }


    private SSLContext prepareContext() {
        try {
            SSLContext sslCtx = SSLContext.getInstance("TLS");
            sslCtx.init(null, new TrustManager[]{new SSLTrustingX509TrustManager()}, null);
            return sslCtx;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private class SSLTrustingX509TrustManager implements X509TrustManager {
        private X509Certificate[] accepted;

        @Override
        public void checkClientTrusted(X509Certificate[] xcs, String string) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] xcs, String string) {
            accepted = xcs;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return accepted;
        }
    }
}