package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// skywalking-logger
public class SkywalkingLogger { 
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
// service_instance_name
@SerializedName("service_instance_name")
@Expose
public String serviceInstanceName;
// endpoint_addr
@SerializedName("endpoint_addr")
@Expose
public String endpointAddr;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// service_name
@SerializedName("service_name")
@Expose
public String serviceName;
// name
@SerializedName("name")
@Expose
public String name;
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
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// batch_max_size
@SerializedName("batch_max_size")
@Expose
public Integer batchMaxSize;
// metadata_schema
// private Object logFormat;
}