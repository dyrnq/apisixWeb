package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// basic-auth

public class BasicAuth {

    public static final String PLUGIN_NAME = "basic-auth";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // hide_credentials
    @SerializedName("hide_credentials")
    @Expose
    public boolean hideCredentials;
    // consumer_schema
    // username
    @SerializedName("username")
    @Expose
    public String username;
    // consumer_schema
    // password
    @SerializedName("password")
    @Expose
    public String password;
}
