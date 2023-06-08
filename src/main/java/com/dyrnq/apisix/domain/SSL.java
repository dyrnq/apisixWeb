package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class SSL{
    public SSL(){
        super();
    }
    @SerializedName("cert")
    @Expose
    private String cert;

    public void setCert(String cert){
        this.cert=cert;
    }

    public String getCert(){
        return this.cert;
    }

    @SerializedName("certs")
    @Expose
    private List<String> certs;

    public void setCerts(List<String> certs){
        this.certs=certs;
    }

    public List<String> getCerts(){
        return this.certs;
    }

    //@SerializedName("client.ca")
    //@Expose
    //private String client.ca;

    //@SerializedName("client.depth")
    //@Expose
    //private String client.depth;

    //@SerializedName("client.skip_mtls_uri_regex")
    //@Expose
    //private String client.skipMtlsUriRegex;

    @SerializedName("create_time")
    @Expose
    private Long createTime;

    public void setCreateTime(Long createTime){
        this.createTime=createTime;
    }

    public Long getCreateTime(){
        return this.createTime;
    }

    @SerializedName("key")
    @Expose
    private String key;

    public void setKey(String key){
        this.key=key;
    }

    public String getKey(){
        return this.key;
    }

    @SerializedName("keys")
    @Expose
    private List<String> keys;

    public void setKeys(List<String> keys){
        this.keys=keys;
    }

    public List<String> getKeys(){
        return this.keys;
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

    @SerializedName("snis")
    @Expose
    private List<String> snis;

    public void setSnis(List<String> snis){
        this.snis=snis;
    }

    public List<String> getSnis(){
        return this.snis;
    }

    @SerializedName("status")
    @Expose
    private Integer status;

    public void setStatus(Integer status){
        this.status=status;
    }

    public Integer getStatus(){
        return this.status;
    }

    @SerializedName("type")
    @Expose
    private String type;

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return this.type;
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
