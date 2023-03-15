package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// real-ip
public class RealIp { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// trusted_addresses
@SerializedName("trusted_addresses")
@Expose
public String[] trustedAddresses;
// recursive
@SerializedName("recursive")
@Expose
public boolean recursive;
// source
@SerializedName("source")
@Expose
public String source;
}