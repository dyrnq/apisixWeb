package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {
    public Client() {
        super();
    }

    @SerializedName("ca")
    @Expose
    private String ca;

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getCa() {
        return this.ca;
    }

    @SerializedName("depth")
    @Expose
    private Integer depth;

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getDepth() {
        return this.depth;
    }

    @SerializedName("skip_mtls_uri_regex")
    @Expose
    private String[] skipMtlsUriRegex;

    public void setSkipMtlsUriRegex(String[] skipMtlsUriRegex) {
        this.skipMtlsUriRegex = skipMtlsUriRegex;
    }

    public String[] getSkipMtlsUriRegex() {
        return this.skipMtlsUriRegex;
    }

    public Client(String ca, Integer depth, String[] skipMtlsUriRegex) {
        this.ca = ca;
        this.depth = depth;
        this.skipMtlsUriRegex = skipMtlsUriRegex;
    }
}
