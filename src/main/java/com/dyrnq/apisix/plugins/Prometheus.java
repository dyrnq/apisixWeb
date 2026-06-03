package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// prometheus

public class Prometheus {

    public static final String PLUGIN_NAME = "prometheus";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // prefer_name
    @SerializedName("prefer_name")
    @Expose
    public boolean preferName;
}
