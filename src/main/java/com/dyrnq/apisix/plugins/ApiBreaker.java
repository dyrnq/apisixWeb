package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// api-breaker
public class ApiBreaker { 

public static final String PLUGIN_NAME = "api-breaker";
// max_breaker_sec
@SerializedName("max_breaker_sec")
@Expose
public Integer maxBreakerSec;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// healthy
@SerializedName("healthy")
@Expose
public Object healthy;
// unhealthy
@SerializedName("unhealthy")
@Expose
public Object unhealthy;
// break_response_code
@SerializedName("break_response_code")
@Expose
public Integer breakResponseCode;
// break_response_body
@SerializedName("break_response_body")
@Expose
public String breakResponseBody;
// break_response_headers
@SerializedName("break_response_headers")
@Expose
public String[] breakResponseHeaders;
}