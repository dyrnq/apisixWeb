package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter {
    @SerializedName("regex")
    @Expose
    public String regex;
    @SerializedName("scope")
    @Expose
    public String scope;
    @SerializedName("replace")
    @Expose
    public String replace;
    @SerializedName("options")
    @Expose
    public String options;
}
