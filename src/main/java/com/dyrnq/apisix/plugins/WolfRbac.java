package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// wolf-rbac
public class WolfRbac { 
// appid
@SerializedName("appid")
@Expose
public String appid;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// server
@SerializedName("server")
@Expose
public String server;
// header_prefix
@SerializedName("header_prefix")
@Expose
public String headerPrefix;
}