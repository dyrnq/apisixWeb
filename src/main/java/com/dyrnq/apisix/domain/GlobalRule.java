package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class GlobalRule {
    public GlobalRule() {
        super();
    }

    @SerializedName("create_time")
    @Expose
    private Long createTime;

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    @SerializedName("plugins")
    @Expose
    private Map<String, Object> plugins;

    public void setPlugins(Map<String, Object> plugins) {
        this.plugins = plugins;
    }

    public Map<String, Object> getPlugins() {
        return this.plugins;
    }

    @SerializedName("update_time")
    @Expose
    private Long updateTime;

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    @SerializedName("id")
    @Expose
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
