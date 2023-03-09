package com.dyrnq.apisix.domain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Protocol{
    @SerializedName("name")
    @Expose
    private String name;

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    @SerializedName("conf")
    @Expose
    private String conf;

    public void setConf(String conf){
        this.conf=conf;
    }

    public String getConf(){
        return this.conf;
    }

    public Protocol(String name,String conf){
        this.name=name;
        this.conf=conf;
    }
}
