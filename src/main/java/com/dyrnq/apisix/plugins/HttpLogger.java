package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// http-logger
public class HttpLogger { 
// max_retry_count
@SerializedName("max_retry_count")
@Expose
public Integer maxRetryCount;
// auth_header
@SerializedName("auth_header")
@Expose
public String authHeader;
// buffer_duration
@SerializedName("buffer_duration")
@Expose
public Integer bufferDuration;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// include_req_body
@SerializedName("include_req_body")
@Expose
public boolean includeReqBody;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// retry_delay
@SerializedName("retry_delay")
@Expose
public Integer retryDelay;
// concat_method
@SerializedName("concat_method")
@Expose
public String concatMethod;
// inactive_timeout
@SerializedName("inactive_timeout")
@Expose
public Integer inactiveTimeout;
// name
@SerializedName("name")
@Expose
public String name;
// log_format
@SerializedName("log_format")
@Expose
public Object logFormat;
// include_resp_body_expr
@SerializedName("include_resp_body_expr")
@Expose
public String[] includeRespBodyExpr;
// uri
@SerializedName("uri")
@Expose
public String uri;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// include_resp_body
@SerializedName("include_resp_body")
@Expose
public boolean includeRespBody;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
}