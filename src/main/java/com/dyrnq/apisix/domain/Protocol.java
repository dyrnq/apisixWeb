package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Protocol {
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("superior_id")
    @Expose
    private String superiorId;

    @SerializedName("logger")
    @Expose
    private List<Logger> logger;
    @SerializedName("conf")
    @Expose
    private Map<String, Object> conf;

    public Protocol(String name, Map<String, Object> conf) {
        this.setName(name);
        this.setConf(conf);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getConf() {
        return this.conf;
    }

    public void setConf(Map<String, Object> conf) {
        this.conf = conf;
    }

    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    public List<Logger> getLogger() {
        return logger;
    }

    public void setLogger(List<Logger> logger) {
        this.logger = logger;
    }
}
