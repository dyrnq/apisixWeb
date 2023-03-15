package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// azure-functions
public class AzureFunctions { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// function_uri
@SerializedName("function_uri")
@Expose
public String functionUri;
// keepalive_pool
@SerializedName("keepalive_pool")
@Expose
public Integer keepalivePool;
// keepalive_timeout
@SerializedName("keepalive_timeout")
@Expose
public Integer keepaliveTimeout;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// authorization
@SerializedName("authorization")
@Expose
public Object authorization;
// keepalive
@SerializedName("keepalive")
@Expose
public boolean keepalive;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
}