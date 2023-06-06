package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// ai
public class Ai { 

public static final String PLUGIN_NAME = "ai";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
}