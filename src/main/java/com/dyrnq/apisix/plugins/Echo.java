package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
// echo

public class Echo {

    public static final String PLUGIN_NAME = "echo";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // headers
    @SerializedName("headers")
    @Expose
    public Map<String, String> headers;
    // body
    @SerializedName("body")
    @Expose
    public String body;
    // after_body
    @SerializedName("after_body")
    @Expose
    public String afterBody;
    // before_body
    @SerializedName("before_body")
    @Expose
    public String beforeBody;
}
