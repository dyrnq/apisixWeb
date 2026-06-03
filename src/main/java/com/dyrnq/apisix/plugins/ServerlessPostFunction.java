package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// serverless-post-function

public class ServerlessPostFunction {

    public static final String PLUGIN_NAME = "serverless-post-function";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // functions
    @SerializedName("functions")
    @Expose
    public String[] functions;
    // phase
    @SerializedName("phase")
    @Expose
    public String phase;
}
