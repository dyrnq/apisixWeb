package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// traffic-split
public class TrafficSplit { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// rules
@SerializedName("rules")
@Expose
public String[] rules;
}