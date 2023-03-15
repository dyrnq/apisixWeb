package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// datadog
public class Datadog { 
// max_retry_count
@SerializedName("max_retry_count")
@Expose
public Integer maxRetryCount;
// name
@SerializedName("name")
@Expose
public String name;
// buffer_duration
@SerializedName("buffer_duration")
@Expose
public Integer bufferDuration;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// inactive_timeout
@SerializedName("inactive_timeout")
@Expose
public Integer inactiveTimeout;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
// retry_delay
@SerializedName("retry_delay")
@Expose
public Integer retryDelay;
// prefer_name
@SerializedName("prefer_name")
@Expose
public boolean preferName;
// private Integer port;
// private String[] constantTags;
// private String host;
// private String namespace;
}