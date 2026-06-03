package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Node {
    public Node() {
        super();
    }

    @SerializedName("host")
    @Expose
    private String host;

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return this.host;
    }

    @SerializedName("port")
    @Expose
    private Integer port;

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return this.port;
    }

    @SerializedName("weight")
    @Expose
    private Integer weight;

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public Node(String host, Integer port, Integer weight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }
}
