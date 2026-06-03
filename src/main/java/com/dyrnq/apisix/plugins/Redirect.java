package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// redirect

public class Redirect {

    public static final String PLUGIN_NAME = "redirect";
    // regex_uri
    @SerializedName("regex_uri")
    @Expose
    public String[] regexUri;
    // append_query_string
    @SerializedName("append_query_string")
    @Expose
    public boolean appendQueryString;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // ret_code
    @SerializedName("ret_code")
    @Expose
    public Integer retCode;
    // encode_uri
    @SerializedName("encode_uri")
    @Expose
    public boolean encodeUri;
    // uri
    @SerializedName("uri")
    @Expose
    public String uri;
    // http_to_https
    @SerializedName("http_to_https")
    @Expose
    public boolean httpToHttps;
}
