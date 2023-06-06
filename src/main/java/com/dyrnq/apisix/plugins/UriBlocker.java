package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// uri-blocker
public class UriBlocker { 

public static final String PLUGIN_NAME = "uri-blocker";
// case_insensitive
@SerializedName("case_insensitive")
@Expose
public boolean caseInsensitive;
// rejected_code
@SerializedName("rejected_code")
@Expose
public Integer rejectedCode;
// rejected_msg
@SerializedName("rejected_msg")
@Expose
public String rejectedMsg;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// block_rules
@SerializedName("block_rules")
@Expose
public String[] blockRules;
}