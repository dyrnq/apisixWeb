package com.dyrnq.apisix;

import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.Endpoint;
import com.dyrnq.apisix.profile.HttpProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseClient {
    static Logger logger = LoggerFactory.getLogger(AdminClient.class);
    public static final int HTTP_OK = 200;
    public static final int HTTP_NOT_OK = 400;
    public static final String SDK_VERSION = "0.1.0";

    private Profile profile;
    private Credential credential;
    private String sdkVersion;
    private String apiVersion;
    public Gson gson;


    public BaseClient(Profile profile) {
        this.credential = profile.getCredential();
        this.profile = profile;
        this.sdkVersion = BaseClient.SDK_VERSION;
        this.apiVersion = profile.getVersion();
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create();
    }

    public Profile getProfile() {
        return this.profile;
    }


    protected String doRequest(String reqMethod, String path)  throws ApisixSDKException {
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
        logger.info(strResp);
        return strResp;
    }

    protected String doRequest(Object model, String reqMethod, String path)  throws ApisixSDKException {
            return doRequest(model,reqMethod,path,null);
    }

    protected String doRequest(Object model, String reqMethod, String path,String param)  throws ApisixSDKException {
        String strParam = model!=null ?gson.toJson(model):param;

        logger.info(strParam);
        Response okRsp = doRequest(reqMethod, path, strParam);

        String strResp = null;
        try {
            strResp = okRsp.body().string();
        } catch (IOException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        if (okRsp.code() >= BaseClient.HTTP_NOT_OK) {
            throw new ApisixSDKException(strResp, String.valueOf(okRsp.code()));
        }
        strResp=StringUtils.replace(strResp,"\"list\":{}","\"list\":[]");
        logger.info(strResp);
        return strResp;
    }

    private Response doRequest(String reqMethod, String path, String param)
            throws ApisixSDKException {

        String contentType = "application/json; charset=utf-8";

        Connection conn =
                new Connection(
                        this.profile.getHttpProfile().getConnTimeout(),
                        this.profile.getHttpProfile().getReadTimeout(),
                        this.profile.getHttpProfile().getWriteTimeout(),
                        this.profile);

        Endpoint currentEndpoint = this.profile.getCurrentEndpoint();
        if(currentEndpoint == null){
            throw new ApisixSDKException("none endpoint alive");
        }

        String url = this.profile.getHttpProfile().getProtocol() + currentEndpoint.getDomain() + path;

        Builder hb = new Builder();
        hb.add("Content-Type", contentType)
                .add("Host", currentEndpoint.getDomain())
                .add("X-API-Version", this.apiVersion)
                .add("X-SDK-RequestClient", this.sdkVersion);

        String token = this.credential.getToken();

        if (token != null && !token.isEmpty()) {
            hb.add("X-API-KEY", token);
        }

        Headers headers = hb.build();

        if (reqMethod.equals(HttpProfile.REQ_GET)) {
            return conn.getRequest(url + "?" + param, headers);
        } else if (reqMethod.equals(HttpProfile.REQ_POST)) {
            return conn.postRequest(url, param, headers);
        } else if (reqMethod.equals(HttpProfile.REQ_DELETE)) {
            return conn.deleteRequest(url, headers);
        } else if (reqMethod.equals(HttpProfile.REQ_PUT)) {
            return conn.putRequest(url, param, headers);
        }else if (reqMethod.equals(HttpProfile.REQ_PATCH)) {
            return conn.patchRequest(url, param, headers);
        } else {
            throw new ApisixSDKException("Method only support (GET, POST, PUT, DELETE)");
        }
    }

    public <T extends Object> List<T> arrangeMulti(List<Item<T>> list){
        Item<T> item;
        T model;
        List<T> result = new ArrayList<>();

        if(list!=null){
            for(int i=0; i<list.size();i++){
                item = list.get(i);
                model = item.getValue();
                result.add(model);
            }
        }

        return result;
    }


}

