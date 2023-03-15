package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// proxy-mirror
public class ProxyMirror { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// path_concat_mode
@SerializedName("path_concat_mode")
@Expose
public String pathConcatMode;
// sample_ratio
@SerializedName("sample_ratio")
@Expose
public Number sampleRatio;
// host
@SerializedName("host")
@Expose
public String host;
// path
@SerializedName("path")
@Expose
public String path;
}