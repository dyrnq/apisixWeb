package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// loki-logger

public class LokiLogger {

    public static final String PLUGIN_NAME = "loki-logger";
    // inactive_timeout
    @SerializedName("inactive_timeout")
    @Expose
    public Integer inactiveTimeout;
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
    // include_resp_body_expr
    @SerializedName("include_resp_body_expr")
    @Expose
    public String[] includeRespBodyExpr;
    // include_resp_body
    @SerializedName("include_resp_body")
    @Expose
    public boolean includeRespBody;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // ssl_verify
    @SerializedName("ssl_verify")
    @Expose
    public boolean sslVerify;
    // endpoint_uri
    @SerializedName("endpoint_uri")
    @Expose
    public String endpointUri;
    // keepalive
    @SerializedName("keepalive")
    @Expose
    public boolean keepalive;
    // tenant_id
    @SerializedName("tenant_id")
    @Expose
    public String tenantId;
    // log_format
    @SerializedName("log_format")
    @Expose
    public Object logFormat;
    // log_labels
    @SerializedName("log_labels")
    @Expose
    public Object logLabels;
    // timeout
    @SerializedName("timeout")
    @Expose
    public Integer timeout;
    // include_req_body_expr
    @SerializedName("include_req_body_expr")
    @Expose
    public String[] includeReqBodyExpr;
    // keepalive_pool
    @SerializedName("keepalive_pool")
    @Expose
    public Integer keepalivePool;
    // endpoint_addrs
    @SerializedName("endpoint_addrs")
    @Expose
    public String[] endpointAddrs;
    // batch_max_size
    @SerializedName("batch_max_size")
    @Expose
    public Integer batchMaxSize;
    // name
    @SerializedName("name")
    @Expose
    public String name;
    // keepalive_timeout
    @SerializedName("keepalive_timeout")
    @Expose
    public Integer keepaliveTimeout;
    // include_req_body
    @SerializedName("include_req_body")
    @Expose
    public boolean includeReqBody;
    // metadata_schema
    // private Object logFormat;
}
