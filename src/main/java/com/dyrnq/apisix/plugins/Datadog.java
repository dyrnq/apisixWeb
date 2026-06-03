package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// datadog

public class Datadog {

    public static final String PLUGIN_NAME = "datadog";
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
    // metadata_schema
    // private Integer port;
    // metadata_schema
    // private String[] constantTags;
    // metadata_schema
    // private String host;
    // metadata_schema
    // private String namespace;
}
