package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// referer-restriction
public class RefererRestriction { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// bypass_missing
@SerializedName("bypass_missing")
@Expose
public boolean bypassMissing;
// whitelist
@SerializedName("whitelist")
@Expose
public String[] whitelist;
// message
@SerializedName("message")
@Expose
public String message;
// blacklist
@SerializedName("blacklist")
@Expose
public String[] blacklist;
}