package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// public-api
public class PublicApi { 

public static final String PLUGIN_NAME = "public-api";
// uri
@SerializedName("uri")
@Expose
public String uri;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
}