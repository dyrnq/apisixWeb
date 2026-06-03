package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// opa

public class Opa {

    public static final String PLUGIN_NAME = "opa";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // timeout
    @SerializedName("timeout")
    @Expose
    public Integer timeout;
    // host
    @SerializedName("host")
    @Expose
    public String host;
    // keepalive
    @SerializedName("keepalive")
    @Expose
    public boolean keepalive;
    // keepalive_timeout
    @SerializedName("keepalive_timeout")
    @Expose
    public Integer keepaliveTimeout;
    // with_service
    @SerializedName("with_service")
    @Expose
    public boolean withService;
    // with_route
    @SerializedName("with_route")
    @Expose
    public boolean withRoute;
    // keepalive_pool
    @SerializedName("keepalive_pool")
    @Expose
    public Integer keepalivePool;
    // with_consumer
    @SerializedName("with_consumer")
    @Expose
    public boolean withConsumer;
    // ssl_verify
    @SerializedName("ssl_verify")
    @Expose
    public boolean sslVerify;
    // policy
    @SerializedName("policy")
    @Expose
    public String policy;
    // send_headers_upstream
    @SerializedName("send_headers_upstream")
    @Expose
    public String[] sendHeadersUpstream;
}
