package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// serverless-pre-function
public class ServerlessPreFunction { 

public static final String PLUGIN_NAME = "serverless-pre-function";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// functions
@SerializedName("functions")
@Expose
public String[] functions;
// phase
@SerializedName("phase")
@Expose
public String phase;
}