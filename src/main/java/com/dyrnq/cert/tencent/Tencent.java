package com.dyrnq.cert.tencent;

import cn.hutool.core.util.HexUtil;
import com.dyrnq.cert.tencent.vo.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * https://github.com/TencentCloud/signature-process-demo/blob/main/cvm/signature-v3/java/TencentCloudAPITC3Demo.java
 */
public class Tencent {


    private final static Charset UTF8 = StandardCharsets.UTF_8;

    private final static String CT_JSON = "application/json; charset=utf-8";
    private static final Logger logger = LoggerFactory.getLogger(Tencent.class);
    private String secretId;
    private String secretKey;

    private Tencent() {

    }


    public Tencent(String secretId, String secretKey) {
        this.secretId = secretId;
        this.secretKey = secretKey;
    }

    public static byte[] hmac256(byte[] key, String msg) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return mac.doFinal(msg.getBytes(UTF8));
    }

    public static String sha256Hex(String s) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] d = md.digest(s.getBytes(UTF8));
        return HexUtil.encodeHexStr(d).toLowerCase();
    }

    /**
     * 下载证书 https://cloud.tencent.com/document/api/400/41670
     *
     * @param arg
     * @return
     * @throws Exception
     */
    public DownloadCertificateResult downloadCertificate(DownloadCertificateArg arg) {
        Type type = new TypeToken<ResponseWrap<DownloadCertificateResult>>() {
        }.getType();
        ResponseWrap<DownloadCertificateResult> w = (ResponseWrap<DownloadCertificateResult>) invoke_ssl("DownloadCertificate", "", arg, type);
        return w.getResponse();
    }


    /**
     * 免费证书申请 https://cloud.tencent.com/document/api/400/41678
     *
     * @param arg
     * @return
     * @throws Exception
     */

    public ApplyCertificateResult applyCertificate(ApplyCertificateArg arg) {
        Type type = new TypeToken<ResponseWrap<ApplyCertificateResult>>() {
        }.getType();
        ResponseWrap<ApplyCertificateResult> w = (ResponseWrap<ApplyCertificateResult>) invoke_ssl("ApplyCertificate", "", arg, type);
        return w.getResponse();
    }

    /**
     * 获取证书信息 https://cloud.tencent.com/document/product/400/41674
     *
     * @param arg
     * @return
     */

    public DescribeCertificateResult describeCertificate(DescribeCertificateArg arg) {
        Type type = new TypeToken<ResponseWrap<DescribeCertificateResult>>() {
        }.getType();
        ResponseWrap<DescribeCertificateResult> w = (ResponseWrap<DescribeCertificateResult>) invoke_ssl("DescribeCertificate", "", arg, type);
        return w.getResponse();
    }


    /**
     * 获取证书列表 https://cloud.tencent.com/document/product/400/41671
     *
     * @param arg
     * @return
     * @throws Exception
     */
    public DescribeCertificatesResult describeCertificates(DescribeCertificatesArg arg) {
        Type type = new TypeToken<ResponseWrap<DescribeCertificatesResult>>() {
        }.getType();
        ResponseWrap<DescribeCertificatesResult> w = (ResponseWrap<DescribeCertificatesResult>) invoke_ssl("DescribeCertificates", "", arg, type);
        return w.getResponse();
    }

    private ResponseWrap<?> invoke_ssl(String action, String region, Object arg, Type type) {
        String service = "ssl";
        String host = "ssl.tencentcloudapi.com";
        String version = "2019-12-05";
        return invoke(service, host, version, action, region, arg, type);
    }

    private ResponseWrap<?> invoke_cvm(String action, String region, Object arg, Type type) {
        String service = "cvm";
        String host = "cvm.tencentcloudapi.com";
        String version = "2017-03-12";
        return invoke(service, host, version, action, region, arg, type);
    }

    public DescribeRegionsResult describeRegions() {
        Type type = new TypeToken<ResponseWrap<DescribeRegionsResult>>() {
        }.getType();
        ResponseWrap<DescribeRegionsResult> w = (ResponseWrap<DescribeRegionsResult>) invoke_cvm("DescribeRegions", "", new HashMap<>(), type);
        return w.getResponse();
    }


    private ResponseWrap<?> invoke(String service, String host, String version, String action, String region, Object arg, Type type) {
        String SECRET_KEY = this.secretKey;
        String SECRET_ID = this.secretId;


        String algorithm = "TC3-HMAC-SHA256";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 注意时区，否则容易出错
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(Long.valueOf(timestamp + "000")));

        // ************* 步骤 1：拼接规范请求串 *************
        String httpRequestMethod = "POST";
        String canonicalUri = "/";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json; charset=utf-8\n" + "host:" + host + "\n";
        String signedHeaders = "content-type;host";

        String payload = "";
        Gson gson = new Gson();
        payload = gson.toJson(arg);
        //logger.info(payload);
        String hashedRequestPayload = sha256Hex(payload);
        String canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;


        // ************* 步骤 2：拼接待签名字符串 *************
        String credentialScope = date + "/" + service + "/" + "tc3_request";
        String hashedCanonicalRequest = sha256Hex(canonicalRequest);
        String stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        //logger.info(stringToSign);
        // ************* 步骤 3：计算签名 *************
        byte[] secretDate = hmac256(("TC3" + SECRET_KEY).getBytes(UTF8), date);
        byte[] secretService = hmac256(secretDate, service);
        byte[] secretSigning = hmac256(secretService, "tc3_request");
        String signature = HexUtil.encodeHexStr(hmac256(secretSigning, stringToSign)).toLowerCase();

        // ************* 步骤 4：拼接 Authorization *************
        String authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;


        Headers.Builder hb = new Headers.Builder();
        hb.add("Content-Type", CT_JSON)
                .add("Authorization", authorization)
                .add("Host", host)
                .add("X-TC-Action", action)
                .add("X-TC-Timestamp", timestamp)
                .add("X-TC-Version", version)
                .add("X-TC-Region", region);
        Headers headers = hb.build();
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        Request request =
                new Request.Builder()
                        .url("https://" + host)
                        .post(RequestBody.create(contentType, payload))
                        .headers(headers)
                        .build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        Response response = null;
        String jsonResponse = null;
        try {
            response = client.newCall(request).execute();
            jsonResponse = response.body().string();
            logger.debug(jsonResponse);

            boolean error = false;
            try {
                JsonPath.read(jsonResponse, "$.Response.Error");
                error = true;
            } catch (com.jayway.jsonpath.PathNotFoundException e) {
            }

            if (error) {
                String code = JsonPath.read(jsonResponse, "$.Response.Error.Code");
                String message = JsonPath.read(jsonResponse, "$.Response.Error.Message");
                throw new TencentRuntimeException(code + " " + message);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new TencentRuntimeException(e);
        }

        ResponseWrap<?> w = gson.fromJson(jsonResponse, type);

        return w;
    }
}
