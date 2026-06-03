package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// chaitin-waf
public class ChaitinWaf {

    public static final String PLUGIN_NAME = "chaitin-waf";
    // match
    @SerializedName("match")
    @Expose
    public String[] match;
    // append_waf_resp_header
    @SerializedName("append_waf_resp_header")
    @Expose
    public boolean appendWafRespHeader;
    // append_waf_debug_header
    @SerializedName("append_waf_debug_header")
    @Expose
    public boolean appendWafDebugHeader;
    // config
    @SerializedName("config")
    @Expose
    public Object config;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // metadata_schema
    // private Object config;
    // metadata_schema
    // private String[] nodes;
}
