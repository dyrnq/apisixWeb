package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// forward-auth
public class ForwardAuth { 

public static final String PLUGIN_NAME = "forward-auth";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// keepalive
@SerializedName("keepalive")
@Expose
public boolean keepalive;
// keepalive_pool
@SerializedName("keepalive_pool")
@Expose
public Integer keepalivePool;
// request_headers
@SerializedName("request_headers")
@Expose
public String[] requestHeaders;
// request_method
@SerializedName("request_method")
@Expose
public String requestMethod;
// keepalive_timeout
@SerializedName("keepalive_timeout")
@Expose
public Integer keepaliveTimeout;
// client_headers
@SerializedName("client_headers")
@Expose
public String[] clientHeaders;
// uri
@SerializedName("uri")
@Expose
public String uri;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// upstream_headers
@SerializedName("upstream_headers")
@Expose
public String[] upstreamHeaders;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// allow_degradation
@SerializedName("allow_degradation")
@Expose
public boolean allowDegradation;
}