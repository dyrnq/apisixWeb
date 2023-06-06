package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// consumer-restriction
public class ConsumerRestriction { 

public static final String PLUGIN_NAME = "consumer-restriction";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// rejected_code
@SerializedName("rejected_code")
@Expose
public Integer rejectedCode;
// whitelist
@SerializedName("whitelist")
@Expose
public String[] whitelist;
// blacklist
@SerializedName("blacklist")
@Expose
public String[] blacklist;
// type
@SerializedName("type")
@Expose
public String type;
// rejected_msg
@SerializedName("rejected_msg")
@Expose
public String rejectedMsg;
// allowed_by_methods
@SerializedName("allowed_by_methods")
@Expose
public String[] allowedByMethods;
}