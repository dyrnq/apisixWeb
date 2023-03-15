package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// ext-plugin-post-resp
public class ExtPluginPostResp { 
// allow_degradation
@SerializedName("allow_degradation")
@Expose
public boolean allowDegradation;
// conf
@SerializedName("conf")
@Expose
public String[] conf;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
}