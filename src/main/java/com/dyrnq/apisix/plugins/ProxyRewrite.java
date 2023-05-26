package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// proxy-rewrite
public class ProxyRewrite { 
// regex_uri
@SerializedName("regex_uri")
@Expose
public String[] regexUri;
// headers
@SerializedName("headers")
@Expose
public Headers headers;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// uri
@SerializedName("uri")
@Expose
public String uri;
// use_real_request_uri_unsafe
@SerializedName("use_real_request_uri_unsafe")
@Expose
public boolean useRealRequestUriUnsafe;
// host
@SerializedName("host")
@Expose
public String host;
// method
@SerializedName("method")
@Expose
public String method;
}