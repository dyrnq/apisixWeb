package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// proxy-control

public class ProxyControl {

    public static final String PLUGIN_NAME = "proxy-control";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // request_buffering
    @SerializedName("request_buffering")
    @Expose
    public boolean requestBuffering;
}
