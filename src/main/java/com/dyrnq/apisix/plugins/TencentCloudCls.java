package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// tencent-cloud-cls

public class TencentCloudCls {

    public static final String PLUGIN_NAME = "tencent-cloud-cls";
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
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // cls_host
    @SerializedName("cls_host")
    @Expose
    public String clsHost;
    // retry_delay
    @SerializedName("retry_delay")
    @Expose
    public Integer retryDelay;
    // inactive_timeout
    @SerializedName("inactive_timeout")
    @Expose
    public Integer inactiveTimeout;
    // sample_ratio
    @SerializedName("sample_ratio")
    @Expose
    public Number sampleRatio;
    // include_resp_body
    @SerializedName("include_resp_body")
    @Expose
    public boolean includeRespBody;
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
    // cls_topic
    @SerializedName("cls_topic")
    @Expose
    public String clsTopic;
    // secret_id
    @SerializedName("secret_id")
    @Expose
    public String secretId;
    // global_tag
    @SerializedName("global_tag")
    @Expose
    public Object globalTag;
    // batch_max_size
    @SerializedName("batch_max_size")
    @Expose
    public Integer batchMaxSize;
    // metadata_schema
    // private Object logFormat;
}
