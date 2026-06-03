package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// clickhouse-logger

public class ClickhouseLogger {

    public static final String PLUGIN_NAME = "clickhouse-logger";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // timeout
    @SerializedName("timeout")
    @Expose
    public Integer timeout;
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
    // include_req_body_expr
    @SerializedName("include_req_body_expr")
    @Expose
    public String[] includeReqBodyExpr;
    // user
    @SerializedName("user")
    @Expose
    public String user;
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
    // endpoint_addr
    @SerializedName("endpoint_addr")
    @Expose
    public String endpointAddr;
    // endpoint_addrs
    @SerializedName("endpoint_addrs")
    @Expose
    public String[] endpointAddrs;
    // name
    @SerializedName("name")
    @Expose
    public String name;
    // password
    @SerializedName("password")
    @Expose
    public String password;
    // include_resp_body_expr
    @SerializedName("include_resp_body_expr")
    @Expose
    public String[] includeRespBodyExpr;
    // ssl_verify
    @SerializedName("ssl_verify")
    @Expose
    public boolean sslVerify;
    // logtable
    @SerializedName("logtable")
    @Expose
    public String logtable;
    // include_resp_body
    @SerializedName("include_resp_body")
    @Expose
    public boolean includeRespBody;
    // database
    @SerializedName("database")
    @Expose
    public String database;
    // metadata_schema
    // private Object logFormat;
}
