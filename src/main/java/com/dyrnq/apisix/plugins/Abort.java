package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Abort {
    @SerializedName("http_status")
    @Expose
    public Integer httpStatus;

    @SerializedName("body")
    @Expose
    public String body;

    @SerializedName("headers")
    @Expose
    public Map<String,String> headers;

    @SerializedName("percentage")
    @Expose
    public Integer percentage;

    @SerializedName("vars")
    @Expose
    public String[] vars;
}
