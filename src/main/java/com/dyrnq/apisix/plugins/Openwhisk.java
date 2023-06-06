package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// openwhisk
public class Openwhisk { 

public static final String PLUGIN_NAME = "openwhisk";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// namespace
@SerializedName("namespace")
@Expose
public String namespace;
// package
@SerializedName("package")
@Expose
public String _package;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// api_host
@SerializedName("api_host")
@Expose
public String apiHost;
// keepalive_timeout
@SerializedName("keepalive_timeout")
@Expose
public Integer keepaliveTimeout;
// service_token
@SerializedName("service_token")
@Expose
public String serviceToken;
// keepalive_pool
@SerializedName("keepalive_pool")
@Expose
public Integer keepalivePool;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// action
@SerializedName("action")
@Expose
public String action;
// keepalive
@SerializedName("keepalive")
@Expose
public boolean keepalive;
// result
@SerializedName("result")
@Expose
public boolean result;
}