package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreamRoute {
    public StreamRoute() {
        super();
    }
    // @SerializedName("protocol.conf")
    // @Expose
    // private String protocol.conf;

    // @SerializedName("protocol.name")
    // @Expose
    // private String protocol.name;

    @SerializedName("remote_addr")
    @Expose
    private String remoteAddr;

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    @SerializedName("server_addr")
    @Expose
    private String serverAddr;

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getServerAddr() {
        return this.serverAddr;
    }

    @SerializedName("server_port")
    @Expose
    private Integer serverPort;

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getServerPort() {
        return this.serverPort;
    }

    @SerializedName("sni")
    @Expose
    private String sni;

    public void setSni(String sni) {
        this.sni = sni;
    }

    public String getSni() {
        return this.sni;
    }

    @SerializedName("upstream")
    @Expose
    private Upstream upstream;

    public void setUpstream(Upstream upstream) {
        this.upstream = upstream;
    }

    public Upstream getUpstream() {
        return this.upstream;
    }

    @SerializedName("upstream_id")
    @Expose
    private String upstreamId;

    public void setUpstreamId(String upstreamId) {
        this.upstreamId = upstreamId;
    }

    public String getUpstreamId() {
        return this.upstreamId;
    }

    @SerializedName("protocol")
    @Expose
    private Protocol protocol;

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    @SerializedName("id")
    @Expose
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @SerializedName("service_id")
    @Expose
    private String serviceId;

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return this.serviceId;
    }
}
