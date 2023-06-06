package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// gzip
public class Gzip { 

public static final String PLUGIN_NAME = "gzip";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// min_length
@SerializedName("min_length")
@Expose
public Integer minLength;
// comp_level
@SerializedName("comp_level")
@Expose
public Integer compLevel;
// buffers
@SerializedName("buffers")
@Expose
public Object buffers;
// types
@SerializedName("types")
@Expose
public Object types;
// vary
@SerializedName("vary")
@Expose
public boolean vary;
// http_version
@SerializedName("http_version")
@Expose
public Object httpVersion;
}