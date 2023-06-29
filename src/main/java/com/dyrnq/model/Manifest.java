package com.dyrnq.model;

import org.noear.wood.annotation.Column;
import org.noear.wood.annotation.Exclude;
import org.noear.wood.annotation.PrimaryKey;
import org.noear.wood.annotation.Table;

@Table("manifest")
public class Manifest {
    @Column("id")
    @PrimaryKey
    private
    String id;
    @Column("title")
    private String title;

    @Exclude
    private
    String content;

    @Exclude
    private
    Long ver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }
}
