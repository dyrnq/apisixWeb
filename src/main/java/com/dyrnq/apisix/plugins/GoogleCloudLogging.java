package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// google-cloud-logging
public class GoogleCloudLogging { 
// auth_config
@SerializedName("auth_config")
@Expose
public Object authConfig;
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
// max_retry_count
@SerializedName("max_retry_count")
@Expose
public Integer maxRetryCount;
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
// resource
@SerializedName("resource")
@Expose
public Object resource;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// auth_file
@SerializedName("auth_file")
@Expose
public String authFile;
// log_id
@SerializedName("log_id")
@Expose
public String logId;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
// metadata_schema
// private Object logFormat;
}