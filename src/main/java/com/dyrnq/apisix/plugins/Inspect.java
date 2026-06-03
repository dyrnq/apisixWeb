package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// inspect

public class Inspect {

    public static final String PLUGIN_NAME = "inspect";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
}
