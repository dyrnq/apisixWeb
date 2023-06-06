package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// splunk-hec-logging
public class SplunkHecLogging { 

public static final String PLUGIN_NAME = "splunk-hec-logging";
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
// name
@SerializedName("name")
@Expose
public String name;
// log_format
@SerializedName("log_format")
@Expose
public Object logFormat;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// endpoint
@SerializedName("endpoint")
@Expose
public Object endpoint;
// inactive_timeout
@SerializedName("inactive_timeout")
@Expose
public Integer inactiveTimeout;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
// metadata_schema
// private Object logFormat;
}