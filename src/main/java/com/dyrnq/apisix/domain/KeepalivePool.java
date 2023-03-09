package com.dyrnq.apisix.domain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class KeepalivePool{
    public KeepalivePool(){
        super();
    }
    @SerializedName("idle_timeout")
    @Expose
    private Integer idleTimeout;

    public void setIdleTimeout(Integer idleTimeout){
        this.idleTimeout=idleTimeout;
    }

    public Integer getIdleTimeout(){
        return this.idleTimeout;
    }

    @SerializedName("requests")
    @Expose
    private Integer requests;

    public void setRequests(Integer requests){
        this.requests=requests;
    }

    public Integer getRequests(){
        return this.requests;
    }

    @SerializedName("size")
    @Expose
    private Integer size;

    public void setSize(Integer size){
        this.size=size;
    }

    public Integer getSize(){
        return this.size;
    }

    public KeepalivePool(Integer idleTimeout,Integer requests,Integer size){
        this.idleTimeout=idleTimeout;
        this.requests=requests;
        this.size=size;
    }
}
