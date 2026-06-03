package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// csrf

public class Csrf {

    public static final String PLUGIN_NAME = "csrf";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // expires
    @SerializedName("expires")
    @Expose
    public Integer expires;
    // name
    @SerializedName("name")
    @Expose
    public String name;
    // key
    @SerializedName("key")
    @Expose
    public String key;
}
