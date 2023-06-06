package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// fault-injection
public class FaultInjection { 

public static final String PLUGIN_NAME = "fault-injection";
// delay
@SerializedName("delay")
@Expose
public Object delay;
// abort
@SerializedName("abort")
@Expose
public Object abort;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
}