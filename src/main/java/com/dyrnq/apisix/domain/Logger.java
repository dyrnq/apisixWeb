package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Logger {

    /**
     * logger filter rules
     */
    @SerializedName("filter")
    @Expose
    private List<Object> filter = new ArrayList<Object>();
    /**
     * logger plugin configuration
     */
    @SerializedName("conf")
    @Expose
    private Map<String, Object> conf;

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * logger filter rules
     */
    public List<Object> getFilter() {
        return filter;
    }

    /**
     * logger filter rules
     */
    public void setFilter(List<Object> filter) {
        this.filter = filter;
    }

    /**
     * logger plugin configuration
     */
    public Map<String, Object> getConf() {
        return conf;
    }

    /**
     * logger plugin configuration
     */
    public void setConf(Map<String, Object> conf) {
        this.conf = conf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
