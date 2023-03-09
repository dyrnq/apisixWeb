package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class PluginMetadata{
    public PluginMetadata(){
        super();
    }
    @SerializedName("subsystem")
    @Expose
    private String subsystem;

    public void setSubsystem(String subsystem){
        this.subsystem=subsystem;
    }

    public String getSubsystem(){
        return this.subsystem;
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
