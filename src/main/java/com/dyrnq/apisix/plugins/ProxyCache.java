package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// proxy-cache

public class ProxyCache {

    public static final String PLUGIN_NAME = "proxy-cache";
    // cache_control
    @SerializedName("cache_control")
    @Expose
    public boolean cacheControl;
    // cache_bypass
    @SerializedName("cache_bypass")
    @Expose
    public String[] cacheBypass;
    // no_cache
    @SerializedName("no_cache")
    @Expose
    public String[] noCache;
    // cache_ttl
    @SerializedName("cache_ttl")
    @Expose
    public Integer cacheTtl;
    // cache_zone
    @SerializedName("cache_zone")
    @Expose
    public String cacheZone;
    // cache_strategy
    @SerializedName("cache_strategy")
    @Expose
    public String cacheStrategy;
    // cache_http_status
    @SerializedName("cache_http_status")
    @Expose
    public String[] cacheHttpStatus;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // cache_method
    @SerializedName("cache_method")
    @Expose
    public String[] cacheMethod;
    // cache_key
    @SerializedName("cache_key")
    @Expose
    public String[] cacheKey;
    // hide_cache_headers
    @SerializedName("hide_cache_headers")
    @Expose
    public boolean hideCacheHeaders;
}
