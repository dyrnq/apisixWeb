package com.dyrnq.model;

import org.noear.wood.annotation.Column;
import org.noear.wood.annotation.PrimaryKey;
import org.noear.wood.annotation.Table;

import java.util.Date;

@Table("deploy")
public class Deploy {
    @Column("id")
    @PrimaryKey
    private
    String id;

    @Column("insert_time")
    private
    Date insertTime;

    @Column("update_time")
    private
    Date updateTime;

    @Column("manifest_id")
    private
    String manifestId;

    @Column("manifest_ver")
    private
    Long manifestVer;

    @Column("inst_id")
    private
    String instId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getManifestId() {
        return manifestId;
    }

    public void setManifestId(String manifestId) {
        this.manifestId = manifestId;
    }

    public Long getManifestVer() {
        return manifestVer;
    }

    public void setManifestVer(Long manifestVer) {
        this.manifestVer = manifestVer;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }
}
