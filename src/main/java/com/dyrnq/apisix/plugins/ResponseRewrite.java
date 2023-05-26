package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
import java.util.List; 
// response-rewrite
public class ResponseRewrite { 
// body_base64
@SerializedName("body_base64")
@Expose
public boolean bodyBase64;
// vars
@SerializedName("vars")
@Expose
public String[] vars;
// headers
@SerializedName("headers")
@Expose
public Headers headers;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// body
@SerializedName("body")
@Expose
public String body;
// filters
@SerializedName("filters")
@Expose
public List<Filter> filters;
// status_code
@SerializedName("status_code")
@Expose
public Integer statusCode;
}