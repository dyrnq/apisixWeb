package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// limit-conn

public class LimitConn {

    public static final String PLUGIN_NAME = "limit-conn";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // default_conn_delay
    @SerializedName("default_conn_delay")
    @Expose
    public Number defaultConnDelay;
    // only_use_default_delay
    @SerializedName("only_use_default_delay")
    @Expose
    public boolean onlyUseDefaultDelay;
    // burst
    @SerializedName("burst")
    @Expose
    public Integer burst;
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
    // allow_degradation
    @SerializedName("allow_degradation")
    @Expose
    public boolean allowDegradation;
    // key
    @SerializedName("key")
    @Expose
    public String key;
    // conn
    @SerializedName("conn")
    @Expose
    public Integer conn;
}
