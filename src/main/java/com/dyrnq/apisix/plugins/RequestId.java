package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// request-id
public class RequestId { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// include_in_response
@SerializedName("include_in_response")
@Expose
public boolean includeInResponse;
// header_name
@SerializedName("header_name")
@Expose
public String headerName;
// range_id
@SerializedName("range_id")
@Expose
public Object rangeId;
// algorithm
@SerializedName("algorithm")
@Expose
public String algorithm;
}