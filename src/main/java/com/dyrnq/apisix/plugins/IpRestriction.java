package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// ip-restriction

public class IpRestriction {

    public static final String PLUGIN_NAME = "ip-restriction";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // blacklist
    @SerializedName("blacklist")
    @Expose
    public String[] blacklist;
    // whitelist
    @SerializedName("whitelist")
    @Expose
    public String[] whitelist;
    // message
    @SerializedName("message")
    @Expose
    public String message;
}
