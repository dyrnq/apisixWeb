package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// syslog
public class Syslog { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// host
@SerializedName("host")
@Expose
public String host;
// log_format
@SerializedName("log_format")
@Expose
public Object logFormat;
// include_req_body
@SerializedName("include_req_body")
@Expose
public boolean includeReqBody;
// inactive_timeout
@SerializedName("inactive_timeout")
@Expose
public Integer inactiveTimeout;
// tls
@SerializedName("tls")
@Expose
public boolean tls;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
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
// pool_size
@SerializedName("pool_size")
@Expose
public Integer poolSize;
// flush_limit
@SerializedName("flush_limit")
@Expose
public Integer flushLimit;
// name
@SerializedName("name")
@Expose
public String name;
// sock_type
@SerializedName("sock_type")
@Expose
public String sockType;
// drop_limit
@SerializedName("drop_limit")
@Expose
public Integer dropLimit;
// port
@SerializedName("port")
@Expose
public Integer port;
}