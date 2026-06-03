package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// cas-auth

public class CasAuth {

    public static final String PLUGIN_NAME = "cas-auth";
    // cas_callback_uri
    @SerializedName("cas_callback_uri")
    @Expose
    public String casCallbackUri;
    // logout_uri
    @SerializedName("logout_uri")
    @Expose
    public String logoutUri;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // idp_uri
    @SerializedName("idp_uri")
    @Expose
    public String idpUri;
}
