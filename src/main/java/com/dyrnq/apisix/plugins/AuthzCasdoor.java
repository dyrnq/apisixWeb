package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// authz-casdoor

public class AuthzCasdoor {

    public static final String PLUGIN_NAME = "authz-casdoor";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // client_secret
    @SerializedName("client_secret")
    @Expose
    public String clientSecret;
    // endpoint_addr
    @SerializedName("endpoint_addr")
    @Expose
    public String endpointAddr;
    // client_id
    @SerializedName("client_id")
    @Expose
    public String clientId;
    // callback_url
    @SerializedName("callback_url")
    @Expose
    public String callbackUrl;
}
