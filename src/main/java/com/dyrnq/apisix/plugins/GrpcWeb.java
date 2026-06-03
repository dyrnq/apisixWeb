package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// grpc-web

public class GrpcWeb {

    public static final String PLUGIN_NAME = "grpc-web";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
}
