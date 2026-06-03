package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// server-info

public class ServerInfo {

    public static final String PLUGIN_NAME = "server-info";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
}
