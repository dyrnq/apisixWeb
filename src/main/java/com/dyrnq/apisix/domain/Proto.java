package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Proto {
    public Proto() {
        super();
    }

    @SerializedName("content")
    @Expose
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
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

    @SerializedName("id")
    @Expose
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
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
}
