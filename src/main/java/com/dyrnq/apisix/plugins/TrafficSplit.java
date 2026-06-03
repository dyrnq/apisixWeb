package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// traffic-split

public class TrafficSplit {

    public static final String PLUGIN_NAME = "traffic-split";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // rules
    @SerializedName("rules")
    @Expose
    public String[] rules;
}
