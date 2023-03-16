package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class Consumer{
    public Consumer(){
        super();
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

    @SerializedName("desc")
    @Expose
    private String desc;

    public void setDesc(String desc){
        this.desc=desc;
    }

    public String getDesc(){
        return this.desc;
    }

    @SerializedName("group_id")
    @Expose
    private String groupId;

    public void setGroupId(String groupId){
        this.groupId=groupId;
    }

    public String getGroupId(){
        return this.groupId;
    }

    @SerializedName("labels")
    @Expose
    private Map<String,String> labels;

    public void setLabels(Map<String,String> labels){
        this.labels=labels;
    }

    public Map<String,String> getLabels(){
        return this.labels;
    }

    @SerializedName("plugins")
    @Expose
    private Map<String,Object> plugins;

    public void setPlugins(Map<String,Object> plugins){
        this.plugins=plugins;
    }

    public Map<String,Object> getPlugins(){
        return this.plugins;
    }

    @SerializedName("update_time")
    @Expose
    private Long updateTime;

    public void setUpdateTime(Long updateTime){
        this.updateTime=updateTime;
    }

    public Long getUpdateTime(){
        return this.updateTime;
    }

    @SerializedName("username")
    @Expose
    private String username;

    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return this.username;
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
