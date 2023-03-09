package com.dyrnq.apisix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Response;

import com.apiseven.apisix.admin.model.response.Item;
import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.http.Connection;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.Endpoint;
import com.apiseven.apisix.common.profile.HttpProfile;
import com.apiseven.apisix.common.profile.Profile;

public abstract class BaseClient {

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
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    public Profile getProfile() {
        return this.profile;
    }


    protected String doRequest(String reqMethod, String path)  throws ApisixSDKExcetion {
        Response okRsp = doRequest(reqMethod, path, "");
        String strResp = null;
        try {
            strResp = okRsp.body().string();
        } catch (IOException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        if (okRsp.code() >= BaseClient.HTTP_NOT_OK) {
            throw new ApisixSDKExcetion(strResp, String.valueOf(okRsp.code()));
        }
        System.out.print(strResp);;
        return strResp;
    }

    protected String doRequest(Object model, String reqMethod, String path)  throws ApisixSDKExcetion {
        String strParam = gson.toJson(model);
        
        System.out.println("=====>"+strParam);
        Response okRsp = doRequest(reqMethod, path, strParam);

        String strResp = null;
        try {
            strResp = okRsp.body().string();
        } catch (IOException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        if (okRsp.code() >= BaseClient.HTTP_NOT_OK) {
            throw new ApisixSDKExcetion(strResp, String.valueOf(okRsp.code()));
        }

        return strResp;
    }

    private Response doRequest(String reqMethod, String path, String param)
            throws ApisixSDKExcetion {

        String contentType = "application/json; charset=utf-8";

        Connection conn =
                new Connection(
                        this.profile.getHttpProfile().getConnTimeout(),
                        this.profile.getHttpProfile().getReadTimeout(),
                        this.profile.getHttpProfile().getWriteTimeout(),
                        this.profile);

        Endpoint currentEndpoint = this.profile.getCurrentEndpoint();
        if(currentEndpoint == null){
            throw new ApisixSDKExcetion("none endpoint alive");
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
            throw new ApisixSDKExcetion("Method only support (GET, POST, PUT, DELETE)");
        }
    }

    protected <T extends Object> List<T> arrangeMulti(List<Item<T>> list){
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

