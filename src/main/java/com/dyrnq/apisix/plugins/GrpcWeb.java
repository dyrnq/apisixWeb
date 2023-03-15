package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// grpc-web
public class GrpcWeb { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
}