package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// authz-casbin

public class AuthzCasbin {

    public static final String PLUGIN_NAME = "authz-casbin";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // policy
    @SerializedName("policy")
    @Expose
    public String policy;
    // model_path
    @SerializedName("model_path")
    @Expose
    public String modelPath;
    // policy_path
    @SerializedName("policy_path")
    @Expose
    public String policyPath;
    // model
    @SerializedName("model")
    @Expose
    public String model;
    // username
    @SerializedName("username")
    @Expose
    public String username;
    // metadata_schema
    // private String model;
    // metadata_schema
    // private String policy;
}
