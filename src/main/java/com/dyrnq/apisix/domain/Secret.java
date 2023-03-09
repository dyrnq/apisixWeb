package com.dyrnq.apisix.domain;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;
public class Secret{
    public Secret(){
        super();
    }
    @SerializedName("prefix")
    @Expose
    private String prefix;

    public void setPrefix(String prefix){
        this.prefix=prefix;
    }

    public String getPrefix(){
        return this.prefix;
    }

    @SerializedName("token")
    @Expose
    private String token;

    public void setToken(String token){
        this.token=token;
    }

    public String getToken(){
        return this.token;
    }

    @SerializedName("uri")
    @Expose
    private String uri;

    public void setUri(String uri){
        this.uri=uri;
    }

    public String getUri(){
        return this.uri;
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
