package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// sls-logger
public class SlsLogger { 
// max_retry_count
@SerializedName("max_retry_count")
@Expose
public Integer maxRetryCount;
// retry_delay
@SerializedName("retry_delay")
@Expose
public Integer retryDelay;
// buffer_duration
@SerializedName("buffer_duration")
@Expose
public Integer bufferDuration;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// port
@SerializedName("port")
@Expose
public Integer port;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// host
@SerializedName("host")
@Expose
public String host;
// include_req_body
@SerializedName("include_req_body")
@Expose
public boolean includeReqBody;
// inactive_timeout
@SerializedName("inactive_timeout")
@Expose
public Integer inactiveTimeout;
// project
@SerializedName("project")
@Expose
public String project;
// log_format
@SerializedName("log_format")
@Expose
public Object logFormat;
// access_key_id
@SerializedName("access_key_id")
@Expose
public String accessKeyId;
// access_key_secret
@SerializedName("access_key_secret")
@Expose
public String accessKeySecret;
// name
@SerializedName("name")
@Expose
public String name;
// logstore
@SerializedName("logstore")
@Expose
public String logstore;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
}