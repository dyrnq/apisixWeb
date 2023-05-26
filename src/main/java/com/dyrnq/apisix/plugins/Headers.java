package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Headers {
    @SerializedName("add")
    @Expose
    public Map<String,String> add;
    @SerializedName("set")
    @Expose
    public Map<String,String> set;
    @SerializedName("remove")
    @Expose
    public Map<String,String> remove;
}
