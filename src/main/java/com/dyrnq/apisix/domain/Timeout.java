package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timeout {
    public Timeout() {
        super();
    }

    @SerializedName("connect")
    @Expose
    private Integer connect;

    public void setConnect(Integer connect) {
        this.connect = connect;
    }

    public Integer getConnect() {
        return this.connect;
    }

    @SerializedName("send")
    @Expose
    private Integer send;

    public void setSend(Integer send) {
        this.send = send;
    }

    public Integer getSend() {
        return this.send;
    }

    @SerializedName("read")
    @Expose
    private Integer read;

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getRead() {
        return this.read;
    }

    public Timeout(Integer connect, Integer send, Integer read) {
        this.connect = connect;
        this.send = send;
        this.read = read;
    }
}
