package com.dyrnq.apisix;

import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.profile.RetryInterceptor;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Connection {

    private OkHttpClient client;

    public Connection(Integer connTimeout, Integer readTimeout, Integer writeTimeout, Profile profile) {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(connTimeout,TimeUnit.SECONDS)
                .readTimeout(readTimeout,TimeUnit.SECONDS)
                .writeTimeout(writeTimeout,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new RetryInterceptor(3, profile))
                .build();
//        this.client.setConnectTimeout(connTimeout, TimeUnit.SECONDS);
//        this.client.setReadTimeout(readTimeout, TimeUnit.SECONDS);
//        this.client.setWriteTimeout(writeTimeout, TimeUnit.SECONDS);
//        this.client.setRetryOnConnectionFailure(true);
//        this.client.interceptors().add(new RetryInterceptor(3, profile));
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


    public Response getRequest(String url, Headers headers) throws ApisixSDKException {
        Request request = null;
        try {
            request = new Request.Builder().url(url).headers(headers).get().build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response putRequest(String url, String body, Headers headers)
            throws ApisixSDKException {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        Request request = null;
        try {
            request =
                    new Request.Builder()
                            .url(url)
                            .put(RequestBody.create(contentType, body))
                            .headers(headers)
                            .build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response patchRequest(String url, String body, Headers headers)
            throws ApisixSDKException {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        Request request = null;
        try {
            request =
                    new Request.Builder()
                            .url(url)
                            .patch(RequestBody.create(contentType, body))
                            .headers(headers)
                            .build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response postRequest(String url, String body, Headers headers)
            throws ApisixSDKException {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        Request request = null;
        try {
            request =
                    new Request.Builder()
                            .url(url)
                            .post(RequestBody.create(contentType, body))
                            .headers(headers)
                            .build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response deleteRequest(String url, Headers headers) throws ApisixSDKException {
        Request request = null;
        try {
            request = new Request.Builder().url(url).headers(headers).delete().build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKException(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

}
