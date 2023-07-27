package com.dyrnq.cert.aliyun;

import cn.hutool.core.lang.UUID;
import com.dyrnq.cert.aliyun.vo.CertificateOrder;
import com.dyrnq.cert.aliyun.vo.DescribeCertificateStateResult;
import com.dyrnq.cert.aliyun.vo.Region;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * https://help.aliyun.com/document_detail/108602.html
 * https://www.alibabacloud.com/help/en/vod/signature-method
 */

public class Aliyun {
    private static final Logger logger = LoggerFactory.getLogger(Aliyun.class);
    private String accessSecretId;
    private String acceccSecretKey;

    private Aliyun() {

    }


    public Aliyun(String accessSecretId, String acceccSecretKey) {
        this.accessSecretId = accessSecretId;
        this.acceccSecretKey = acceccSecretKey;
    }


    public static String generateTimestamp() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    public static String generateRandom() {
        String signatureNonce = UUID.randomUUID().toString(true);
        return signatureNonce;
    }


    public String invoke(String url, String version, String action, Map<String, String> q) {
        final String ACCESS_KEY_ID = accessSecretId;
        final String ACCESS_KEY_SECRET = acceccSecretKey;
        Map<String, String> map = new HashMap<String, String>();
        // 公共参数。
        map.put("Format", "JSON");
        map.put("Version", version);
        map.put("AccessKeyId", ACCESS_KEY_ID);
        map.put("SignatureMethod", "HMAC-SHA1");
        map.put("Timestamp", generateTimestamp());
        map.put("SignatureVersion", "1.0");
        map.put("SignatureNonce", generateRandom());
        map.put("Action", action);
        if (q != null) {
            map.putAll(q);
        }
        Gson gson = new Gson();
        String signature = null;
        try {
            signature = SignatureUtils.generate("GET", map, ACCESS_KEY_SECRET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Headers.Builder hb = new Headers.Builder();
        Headers headers = hb.build();
        Request request = new Request.Builder().url(url + "?" + UrlUtil.canonicalizeQueryString(map, true) + "&Signature=" + signature).headers(headers).get().build();


        OkHttpClient client = new OkHttpClient.Builder().build();
        Response response = null;
        String jsonResponse = null;
        try {
            response = client.newCall(request).execute();
            jsonResponse = response.body().string();
            logger.debug(jsonResponse);
            if (response.code() >= 400) {
                Map mapRes = gson.fromJson(jsonResponse, Map.class);
                throw new AliyunRuntimeException(mapRes.get("Message") + " " + mapRes.get("Recommend") + " " + mapRes.get("Code"));
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return jsonResponse;
    }

    public String invoke_ecs(String action, Map<String, String> q) {
        return invoke("https://ecs.aliyuncs.com", "2014-05-26", action, q);
    }

    public String invoke_cas(String action, Map<String, String> q) {
        return invoke("https://cas.aliyuncs.com", "2020-04-07", action, q);
    }

    private String invoke_dns(String action, Map<String, String> q) {
        return invoke("https://dns.aliyuncs.com", "2015-01-09", action, q);
    }

    /**
     * 新增域名记录
     *
     * @param domainName
     * @param RR
     * @param type
     * @param value
     */
    public void addDomainRecord(String domainName, String RR, String type, String value) {
        Map<String, String> q = new HashMap<>();
        q.put("DomainName", domainName);
        q.put("RR", RR);
        q.put("Type", type);
        q.put("Value", value);
        String json = invoke_dns("AddDomainRecord", q);
    }


    public List<Region> describeRegions() {
        String json = invoke_ecs("DescribeRegions", null);
        String jsonResult = JsonPath.read(json, "$.Regions.Region").toString();
        Gson gson = new Gson();

        Type type = new TypeToken<List<Region>>() {
        }.getType();

        return gson.fromJson(jsonResult, type);
    }

    /**
     * https://next.api.aliyun.com/api/cas/2020-04-07/CreateCertificateForPackageRequest
     */
    public Long createCertificateForPackageRequest(String domain, String validateType) {
        Map<String, String> q = new HashMap<>();
        //symantec-free-1-free（默认）：表示DigiCert DV单域名证书（免费试用）。
        q.put("ProductCode", "symantec-free-1-free");
        q.put("Domain", domain);
        q.put("ValidateType", validateType);
        String json = invoke_cas("CreateCertificateForPackageRequest", q);
        Number number = JsonPath.read(json, "$.OrderId");
        return Long.valueOf(number.longValue());
    }

    /**
     * https://next.api.aliyun.com/api/cas/2020-04-07/ListUserCertificateOrder
     *
     * @param keyword
     * @param status
     * @return
     */
    public List<CertificateOrder> listUserCertificateOrder(String keyword, String status) {
        Map<String, String> q = new HashMap<>();
        if (status != null) {
            q.put("Status", status);
        }
        if (keyword != null) {
            q.put("Keyword", keyword);
        }
        String json = invoke_cas("ListUserCertificateOrder", q);
        String jsonResult = JsonPath.read(json, "$.CertificateOrderList").toString();
        Type type = new TypeToken<List<CertificateOrder>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonResult, type);
    }

    public DescribeCertificateStateResult describeCertificateState(Long orderId) {
        Map<String, String> q = new HashMap<>();
        q.put("OrderId", String.valueOf(orderId));
        String json = invoke_cas("DescribeCertificateState", q);

        Type type = new TypeToken<DescribeCertificateStateResult>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }


}
