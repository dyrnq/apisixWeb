package com.dyrnq.apisix.domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Active {

    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("timeout")
    @Expose
    private BigDecimal timeout = new BigDecimal("1");
    @SerializedName("port")
    @Expose
    private Integer port;
    @SerializedName("concurrency")
    @Expose
    private Integer concurrency = 10;
    @SerializedName("http_path")
    @Expose
    private String httpPath = "/";
    @SerializedName("https_verify_certificate")
    @Expose
    private Boolean httpsVerifyCertificate = true;
    @SerializedName("req_headers")
    @Expose
    private List<String> reqHeaders = new ArrayList<String>();
    @SerializedName("unhealthy")
    @Expose
    private Unhealthy__1 unhealthy;
    @SerializedName("healthy")
    @Expose
    private Healthy__1 healthy;
    @SerializedName("type")
    @Expose
    private Type type = Type.fromValue("http");

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public BigDecimal getTimeout() {
        return timeout;
    }

    public void setTimeout(BigDecimal timeout) {
        this.timeout = timeout;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(Integer concurrency) {
        this.concurrency = concurrency;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public Boolean getHttpsVerifyCertificate() {
        return httpsVerifyCertificate;
    }

    public void setHttpsVerifyCertificate(Boolean httpsVerifyCertificate) {
        this.httpsVerifyCertificate = httpsVerifyCertificate;
    }

    public List<String> getReqHeaders() {
        return reqHeaders;
    }

    public void setReqHeaders(List<String> reqHeaders) {
        this.reqHeaders = reqHeaders;
    }

    public Unhealthy__1 getUnhealthy() {
        return unhealthy;
    }

    public void setUnhealthy(Unhealthy__1 unhealthy) {
        this.unhealthy = unhealthy;
    }

    public Healthy__1 getHealthy() {
        return healthy;
    }

    public void setHealthy(Healthy__1 healthy) {
        this.healthy = healthy;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {

        @SerializedName("http")
        HTTP("http"),
        @SerializedName("https")
        HTTPS("https"),
        @SerializedName("tcp")
        TCP("tcp");
        private final static Map<String, Type> CONSTANTS = new HashMap<String, Type>();

        static {
            for (Type c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public static Type fromValue(String value) {
            Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

    }

}
