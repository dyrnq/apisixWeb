package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// fault-injection

public class FaultInjection {

    public static final String PLUGIN_NAME = "fault-injection";
    // delay
    @SerializedName("delay")
    @Expose
    public Delay delay;
    // abort
    @SerializedName("abort")
    @Expose
    public Abort abort;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
}
