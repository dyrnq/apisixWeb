package com.dyrnq.model;

import org.noear.wood.annotation.Column;
import org.noear.wood.annotation.PrimaryKey;
import org.noear.wood.annotation.Table;

@Table("manifest_ver")
public class ManifestVer {
    @Column("id")
    @PrimaryKey
    private
    String id;

    @Column("ver")
    @PrimaryKey
    private
    Long ver;

    @Column("content")
    private
    String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
