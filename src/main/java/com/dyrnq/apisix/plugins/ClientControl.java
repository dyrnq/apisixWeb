package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// client-control
public class ClientControl { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// max_body_size
@SerializedName("max_body_size")
@Expose
public Integer maxBodySize;
}