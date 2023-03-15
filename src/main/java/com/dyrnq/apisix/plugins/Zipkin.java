package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// zipkin
public class Zipkin { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// span_version
@SerializedName("span_version")
@Expose
public Object spanVersion;
// endpoint
@SerializedName("endpoint")
@Expose
public String endpoint;
// sample_ratio
@SerializedName("sample_ratio")
@Expose
public Number sampleRatio;
// service_name
@SerializedName("service_name")
@Expose
public String serviceName;
// server_addr
@SerializedName("server_addr")
@Expose
public String serverAddr;
}