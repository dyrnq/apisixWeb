package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// jwt-auth

public class JwtAuth {

    public static final String PLUGIN_NAME = "jwt-auth";
    // query
    @SerializedName("query")
    @Expose
    public String query;
    // cookie
    @SerializedName("cookie")
    @Expose
    public String cookie;
    // hide_credentials
    @SerializedName("hide_credentials")
    @Expose
    public boolean hideCredentials;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // header
    @SerializedName("header")
    @Expose
    public String header;
    // consumer_schema
    // exp
    @SerializedName("exp")
    @Expose
    public Integer exp;
    // consumer_schema
    // secret
    @SerializedName("secret")
    @Expose
    public String secret;
    // consumer_schema
    // base64_secret
    @SerializedName("base64_secret")
    @Expose
    public boolean base64Secret;
    // consumer_schema
    // key
    @SerializedName("key")
    @Expose
    public String key;
    // consumer_schema
    // lifetime_grace_period
    @SerializedName("lifetime_grace_period")
    @Expose
    public Integer lifetimeGracePeriod;
    // consumer_schema
    // algorithm
    @SerializedName("algorithm")
    @Expose
    public String algorithm;
}
