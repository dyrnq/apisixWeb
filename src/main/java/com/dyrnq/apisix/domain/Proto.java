package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class Proto{
    public Proto(){
        super();
    }
    @SerializedName("content")
    @Expose
    private String content;

    public void setContent(String content){
        this.content=content;
    }

    public String getContent(){
        return this.content;
    }

    @SerializedName("create_time")
    @Expose
    private Long createTime;

    public void setCreateTime(Long createTime){
        this.createTime=createTime;
    }

    public Long getCreateTime(){
        return this.createTime;
    }

    @SerializedName("id")
    @Expose
    private String id;

    public void setId(String id){
        this.id=id;
    }

    public String getId(){
        return this.id;
    }

}
