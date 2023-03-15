package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// ip-restriction
public class IpRestriction { 
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