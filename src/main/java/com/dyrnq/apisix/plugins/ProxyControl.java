package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// proxy-control
public class ProxyControl { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// request_buffering
@SerializedName("request_buffering")
@Expose
public boolean requestBuffering;
}