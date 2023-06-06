package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Delay {
    @SerializedName("duration")
    @Expose
    public Number duration;

    @SerializedName("percentage")
    @Expose
    public Integer percentage;

    @SerializedName("vars")
    @Expose
    public String[] vars;
}
