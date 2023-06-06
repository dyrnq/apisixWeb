package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// workflow
public class Workflow { 

public static final String PLUGIN_NAME = "workflow";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// rules
@SerializedName("rules")
@Expose
public String[] rules;
}