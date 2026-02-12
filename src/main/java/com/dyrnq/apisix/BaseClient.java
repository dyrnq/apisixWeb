package com.dyrnq.apisix;

import cn.hutool.core.net.url.UrlQuery;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.Endpoint;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
//import org.noear.snack.ONode;
import org.noear.snack4.ONode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseClient {
    public static final int HTTP_OK = 200;
    public static final int HTTP_NOT_OK = 400;
    public static final String SDK_VERSION = "0.1.0";
    public static final String QUERY_PARAMS_PAGE = "page";
    public static final String QUERY_PARAMS_PAGE_SIZE = "page_size";
    static Logger logger = LoggerFactory.getLogger(AdminClient.class);
    private final Profile profile;
    private final Credential credential;
    private final String sdkVersion;
    private final String apiVersion;
    public Gson gson;


    public BaseClient(Profile profile) {
        this.credential = profile.getCredential();
        this.profile = profile;
        this.sdkVersion = BaseClient.SDK_VERSION;
        this.apiVersion = profile.getVersion();
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create();
    }

    protected static String mapToQueryString(Map<String, String> params) {
//        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//        }
//
//        return URLEncodedUtils.format(nameValuePairs, StandardCharsets.UTF_8);

        return UrlQuery.of(params, false).build(StandardCharsets.UTF_8);
    }

    public Profile getProfile() {
        return this.profile;
    }

    protected String doRequest(String reqMethod, String path) throws ApisixSDKException {
        Response okRsp = doRequest(reqMethod, path, "");
        String strResp = null;
        try {
            strResp = okRsp.body().string();
        } catch (IOException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        if (okRsp.code() >= BaseClient.HTTP_NOT_OK) {
            throw new ApisixSDKException(strResp, String.valueOf(okRsp.code()));
        }
        logger.debug(strResp);
        return strResp;
    }

    protected String doRequest(Object model, String reqMethod, String path) throws ApisixSDKException {
        return doRequest(model, reqMethod, path, null);
    }

    protected String doRequest(Object model, String reqMethod, String path, String param) throws ApisixSDKException {
        String strParam = model != null ? gson.toJson(model) : param;

        //logger.info(strParam);
        Response okRsp = doRequest(reqMethod, path, strParam);

        String strResp = null;
        try {
            strResp = okRsp.body().string();
        } catch (IOException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        if (okRsp.code() == BaseClient.HTTP_NOT_OK) {
            logger.error(strResp);
            if (strResp != null) {
                Pattern pattern = Pattern.compile("\"error_msg\":\"([^\"]*)\"");
                Matcher matcher = pattern.matcher(strResp);

                if (matcher.find()) {
                    ONode o = ONode.ofJson(strResp);
                    String errorMsg = o.get("error_msg").toString();
                    throw new ApisixSDKException(errorMsg, String.valueOf(okRsp.code()));
                } else {
                    throw new ApisixSDKException(strResp, String.valueOf(okRsp.code()));
                }
            }
        } else if (okRsp.code() > BaseClient.HTTP_NOT_OK) {
            logger.error(strResp);
            throw new ApisixSDKException(String.valueOf(okRsp.code()), String.valueOf(okRsp.code()));
        }
        strResp = StringUtils.replace(strResp, "\"list\":{}", "\"list\":[]");
        logger.debug(strResp);
        return strResp;
    }

    private Response doRequest(String reqMethod, String path, String param)
            throws ApisixSDKException {

        String contentType = "application/json; charset=utf-8";

        Connection conn =
                new Connection(
                        this.profile.timeout().getConn(),
                        this.profile.timeout().getRead(),
                        this.profile.timeout().getWrite(),
                        this.profile);

        Endpoint currentEndpoint = this.profile.getCurrentEndpoint();
        if (currentEndpoint == null) {
            throw new ApisixSDKException("none endpoint alive");
        }

        String url = currentEndpoint.getAddress() + path;

        Builder hb = new Builder();
        hb.add("Content-Type", contentType)
                .add("X-API-Version", this.apiVersion)
                .add("X-SDK-RequestClient", this.sdkVersion);

        String token = this.credential.getToken();

        if (token != null && !token.isEmpty()) {
            hb.add("X-API-KEY", token);
        }

        Headers headers = hb.build();

        switch (reqMethod) {
            case HttpMethod.REQ_GET:
                return conn.getRequest(url + "?" + param, headers);
            case HttpMethod.REQ_POST:
                return conn.postRequest(url, param, headers);
            case HttpMethod.REQ_DELETE:
                return conn.deleteRequest(url, headers);
            case HttpMethod.REQ_PUT:
                return conn.putRequest(url, param, headers);
            case HttpMethod.REQ_PATCH:
                return conn.patchRequest(url, param, headers);
            default:
                throw new ApisixSDKException("Method only support (GET, POST, PUT, DELETE, PATCH)");
        }
    }

    public <T extends Object> List<T> arrangeMulti(List<Item<T>> list) {
        Item<T> item;
        T model;
        List<T> result = new ArrayList<>();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                item = list.get(i);
                model = item.getValue();
                result.add(model);
            }
        }

        return result;
    }
}

