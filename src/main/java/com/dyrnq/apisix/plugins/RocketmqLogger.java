package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// rocketmq-logger

public class RocketmqLogger {

    public static final String PLUGIN_NAME = "rocketmq-logger";
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // topic
    @SerializedName("topic")
    @Expose
    public String topic;
    // meta_format
    @SerializedName("meta_format")
    @Expose
    public String metaFormat;
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
    // tag
    @SerializedName("tag")
    @Expose
    public String tag;
    // access_key
    @SerializedName("access_key")
    @Expose
    public String accessKey;
    // max_retry_count
    @SerializedName("max_retry_count")
    @Expose
    public Integer maxRetryCount;
    // secret_key
    @SerializedName("secret_key")
    @Expose
    public String secretKey;
    // buffer_duration
    @SerializedName("buffer_duration")
    @Expose
    public Integer bufferDuration;
    // batch_max_size
    @SerializedName("batch_max_size")
    @Expose
    public Integer batchMaxSize;
    // name
    @SerializedName("name")
    @Expose
    public String name;
    // retry_delay
    @SerializedName("retry_delay")
    @Expose
    public Integer retryDelay;
    // include_resp_body_expr
    @SerializedName("include_resp_body_expr")
    @Expose
    public String[] includeRespBodyExpr;
    // use_tls
    @SerializedName("use_tls")
    @Expose
    public boolean useTls;
    // nameserver_list
    @SerializedName("nameserver_list")
    @Expose
    public String[] nameserverList;
    // include_resp_body
    @SerializedName("include_resp_body")
    @Expose
    public boolean includeRespBody;
    // key
    @SerializedName("key")
    @Expose
    public String key;
    // metadata_schema
    // private Object logFormat;
}
