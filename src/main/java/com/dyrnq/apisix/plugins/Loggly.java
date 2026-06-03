package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// loggly

public class Loggly {

    public static final String PLUGIN_NAME = "loggly";
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
    // severity
    @SerializedName("severity")
    @Expose
    public String severity;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // include_resp_body_expr
    @SerializedName("include_resp_body_expr")
    @Expose
    public String[] includeRespBodyExpr;
    // inactive_timeout
    @SerializedName("inactive_timeout")
    @Expose
    public Integer inactiveTimeout;
    // customer_token
    @SerializedName("customer_token")
    @Expose
    public String customerToken;
    // severity_map
    @SerializedName("severity_map")
    @Expose
    public Object severityMap;
    // name
    @SerializedName("name")
    @Expose
    public String name;
    // log_format
    @SerializedName("log_format")
    @Expose
    public Object logFormat;
    // tags
    @SerializedName("tags")
    @Expose
    public String[] tags;
    // ssl_verify
    @SerializedName("ssl_verify")
    @Expose
    public boolean sslVerify;
    // include_req_body
    @SerializedName("include_req_body")
    @Expose
    public boolean includeReqBody;
    // include_resp_body
    @SerializedName("include_resp_body")
    @Expose
    public boolean includeRespBody;
    // batch_max_size
    @SerializedName("batch_max_size")
    @Expose
    public Integer batchMaxSize;
    // metadata_schema
    // private Object logFormat;
    // metadata_schema
    // private Integer port;
    // metadata_schema
    // private Integer timeout;
    // metadata_schema
    // private String host;
    // metadata_schema
    // private String protocol;
}
