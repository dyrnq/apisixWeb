package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// ua-restriction
public class UaRestriction { 

public static final String PLUGIN_NAME = "ua-restriction";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// bypass_missing
@SerializedName("bypass_missing")
@Expose
public boolean bypassMissing;
// message
@SerializedName("message")
@Expose
public String message;
// allowlist
@SerializedName("allowlist")
@Expose
public String[] allowlist;
// denylist
@SerializedName("denylist")
@Expose
public String[] denylist;
}