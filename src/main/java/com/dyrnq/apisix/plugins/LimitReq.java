package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// limit-req
public class LimitReq { 

public static final String PLUGIN_NAME = "limit-req";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// rate
@SerializedName("rate")
@Expose
public Number rate;
// burst
@SerializedName("burst")
@Expose
public Number burst;
// key_type
@SerializedName("key_type")
@Expose
public String keyType;
// rejected_code
@SerializedName("rejected_code")
@Expose
public Integer rejectedCode;
// rejected_msg
@SerializedName("rejected_msg")
@Expose
public String rejectedMsg;
// nodelay
@SerializedName("nodelay")
@Expose
public boolean nodelay;
// allow_degradation
@SerializedName("allow_degradation")
@Expose
public boolean allowDegradation;
// key
@SerializedName("key")
@Expose
public String key;
}