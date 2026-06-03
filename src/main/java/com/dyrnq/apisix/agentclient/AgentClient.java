package com.dyrnq.apisix.agentclient;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.HttpMethod;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.*;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentClient {
    static Logger logger = LoggerFactory.getLogger(AgentClient.class);
    static final String API_CONFIG_PATH = "/api/v1/config";
    static final String API_RELOAD_PATH = "/api/v1/reload";
    static final String API_RESTART_PATH = "/api/v1/restart";
    static final String API_STOP_PATH = "/api/v1/stop";
    static final String API_START_PATH = "/api/v1/start";

    private final OkHttpClient client;

    public static final String DEFAULT_JWT_SECRET =
            "IDP32XTulsVIUZU+srFEUC9Lhu1wV+nd8iCJPoPA2zSFVAtWhCgpMEymxy5wFAZKMB9yROX31UjDzjwL66r1RA==";

    public AgentClient() {
        this(6, 6, 6);
    }

    public AgentClient(Integer connTimeout, Integer readTimeout, Integer writeTimeout) {
        this(connTimeout, readTimeout, writeTimeout, true);
    }

    public AgentClient(Integer connTimeout, Integer readTimeout, Integer writeTimeout, boolean sslVerify) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        if (sslVerify) {
            // 标准证书校验（默认使用系统信任库）
            logger.info("AgentClient: SSL verification enabled");
        } else {
            // 信任所有证书（用于自签名证书或内部网络）
            logger.warn("AgentClient: SSL verification disabled, trusting all certificates");
            builder.sslSocketFactory(createUnsafeSSLSocketFactory(), new TrustAllCerts())
                    .hostnameVerifier(new TrustAllHostnameVerifier());
        }
        this.client = builder.build();
    }

    public String readConfig(String host, String token) throws Exception {
        Response r = null;
        r = this.doRequest(host, "GET", API_CONFIG_PATH, token, null);
        String bodyStr = r.body().string();
        if (r.code() == 200) {
            return JsonPath.read(bodyStr, "$.data");
        } else {
            throw new ApisixSDKException(JsonPath.read(bodyStr, "$.error"));
        }
    }

    public String writeConfig(String host, String token, String data) throws Exception {
        Response r = null;
        // 使用 com.google.gson 构建 JSON，避免字符串拼接导致注入
        com.google.gson.JsonObject jsonObj = new com.google.gson.JsonObject();
        jsonObj.addProperty("data", data);
        r = this.doRequest(host, "POST", API_CONFIG_PATH, token, jsonObj.toString());
        if (r.code() == 200) {
            return "";
        }
        throw new ApisixSDKException(JsonPath.read(r.body().string(), "$.error"));
    }

    public String restart(String host, String token) throws Exception {
        Response r = null;
        r = this.doRequest(host, "GET", API_RESTART_PATH, token, null);
        String bodyStr = r.body().string();
        if (r.code() == 200) {
            return JsonPath.read(bodyStr, "$.outStr");
        } else {
            try {
                throw new ApisixSDKException(JsonPath.read(bodyStr, "$.errStr"));
            } catch (com.jayway.jsonpath.PathNotFoundException e) {
                //
            }
            throw new ApisixSDKException(JsonPath.read(bodyStr, "$.error"));
        }
    }

    public String reload(String host, String token) throws Exception {
        Response r = null;
        r = this.doRequest(host, "GET", API_RELOAD_PATH, token, null);
        String bodyStr = r.body().string();
        if (r.code() == 200) {
            return JsonPath.read(bodyStr, "$.outStr");
        } else {
            try {
                throw new ApisixSDKException(JsonPath.read(bodyStr, "$.errStr"));
            } catch (com.jayway.jsonpath.PathNotFoundException e) {
                //
            }
            throw new ApisixSDKException(JsonPath.read(bodyStr, "$.error"));
        }
    }

    public String stop(String host, String token) throws Exception {
        Response r = null;
        r = this.doRequest(host, "GET", API_STOP_PATH, token, null);
        String bodyStr = r.body().string();
        if (r.code() == 200) {
            return JsonPath.read(bodyStr, "$.outStr");
        } else {
            try {
                throw new ApisixSDKException(JsonPath.read(bodyStr, "$.errStr"));
            } catch (com.jayway.jsonpath.PathNotFoundException e) {
                //
            }
            throw new ApisixSDKException(JsonPath.read(bodyStr, "$.error"));
        }
    }

    public String start(String host, String token) throws Exception {
        Response r = null;
        r = this.doRequest(host, "GET", API_START_PATH, token, null);
        String bodyStr = r.body().string();
        if (r.code() == 200) {
            return JsonPath.read(bodyStr, "$.outStr");
        } else {
            try {
                throw new ApisixSDKException(JsonPath.read(bodyStr, "$.errStr"));
            } catch (com.jayway.jsonpath.PathNotFoundException e) {
                //
            }
            throw new ApisixSDKException(JsonPath.read(bodyStr, "$.error"));
        }
    }

    private Response doRequest(String host, String reqMethod, String path, String token, String param)
            throws ApisixSDKException {

        String contentType = "application/json; charset=utf-8";

        String url = host + path;

        Headers.Builder hb = new Headers.Builder();
        hb.add("Content-Type", contentType);
        if (token != null && !token.isEmpty()) {
            hb.add("X-API-KEY", token);
        }

        Headers headers = hb.build();

        switch (reqMethod) {
            case HttpMethod.REQ_GET:
                return getRequest(param != null ? url + "?" + param : url, headers);
            case HttpMethod.REQ_POST:
                return postRequest(url, param, headers);
            default:
                throw new ApisixSDKException("Method only support (GET, POST)");
        }
    }

    private Response postRequest(String url, String body, Headers headers) throws ApisixSDKException {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(contentType, body))
                    .headers(headers)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    private Response getRequest(String url, Headers headers) throws ApisixSDKException {
        Request request = null;
        try {
            request = new Request.Builder().url(url).headers(headers).get().build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response doRequest(Request request) throws ApisixSDKException {
        Response response = null;
        try {
            response = this.client.newCall(request).execute();
        } catch (IOException e) {

            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }
        return response;
    }

    private SSLSocketFactory createUnsafeSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(
                    null,
                    new TrustManager[] {
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType)
                                    throws CertificateException {}

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType)
                                    throws CertificateException {}

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }
                        }
                    },
                    new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
