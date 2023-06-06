package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// elasticsearch-logger
public class ElasticsearchLogger { 

public static final String PLUGIN_NAME = "elasticsearch-logger";
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
// endpoint_addr
@SerializedName("endpoint_addr")
@Expose
public String endpointAddr;
// endpoint_addrs
@SerializedName("endpoint_addrs")
@Expose
public String[] endpointAddrs;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// auth
@SerializedName("auth")
@Expose
public Object auth;
// name
@SerializedName("name")
@Expose
public String name;
// log_format
@SerializedName("log_format")
@Expose
public Object logFormat;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// field
@SerializedName("field")
@Expose
public Object field;
// inactive_timeout
@SerializedName("inactive_timeout")
@Expose
public Integer inactiveTimeout;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
// metadata_schema
// private Object logFormat;
}