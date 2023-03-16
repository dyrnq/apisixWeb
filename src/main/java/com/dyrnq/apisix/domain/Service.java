package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class Service{
    public Service(){
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

    @SerializedName("enable_websocket")
    @Expose
    private Boolean enableWebsocket;

    public void setEnableWebsocket(Boolean enableWebsocket){
        this.enableWebsocket=enableWebsocket;
    }

    public Boolean isEnableWebsocket(){
        return this.enableWebsocket;
    }

    @SerializedName("hosts")
    @Expose
    private List<String> hosts;

    public void setHosts(List<String> hosts){
        this.hosts=hosts;
    }

    public List<String> getHosts(){
        return this.hosts;
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

    @SerializedName("name")
    @Expose
    private String name;

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
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

    @SerializedName("upstream")
    @Expose
    private Upstream upstream;

    public void setUpstream(Upstream upstream){
        this.upstream=upstream;
    }

    public Upstream getUpstream(){
        return this.upstream;
    }

    @SerializedName("upstream_id")
    @Expose
    private String upstreamId;

    public void setUpstreamId(String upstreamId){
        this.upstreamId=upstreamId;
    }

    public String getUpstreamId(){
        return this.upstreamId;
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
