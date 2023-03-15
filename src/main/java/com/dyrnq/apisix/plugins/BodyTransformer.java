package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// body-transformer
public class BodyTransformer { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// request
@SerializedName("request")
@Expose
public Object request;
// response
@SerializedName("response")
@Expose
public Object response;
}