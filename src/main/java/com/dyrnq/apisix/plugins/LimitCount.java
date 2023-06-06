package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// limit-count
public class LimitCount { 

public static final String PLUGIN_NAME = "limit-count";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// rejected_msg
@SerializedName("rejected_msg")
@Expose
public String rejectedMsg;
// key_type
@SerializedName("key_type")
@Expose
public String keyType;
// count
@SerializedName("count")
@Expose
public Integer count;
// rejected_code
@SerializedName("rejected_code")
@Expose
public Integer rejectedCode;
// group
@SerializedName("group")
@Expose
public String group;
// key
@SerializedName("key")
@Expose
public String key;
// allow_degradation
@SerializedName("allow_degradation")
@Expose
public boolean allowDegradation;
// time_window
@SerializedName("time_window")
@Expose
public Integer timeWindow;
// show_limit_quota_header
@SerializedName("show_limit_quota_header")
@Expose
public boolean showLimitQuotaHeader;
// policy
@SerializedName("policy")
@Expose
public String policy;
}