package com.dyrnq.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class X509Holder {

    @SerializedName("cert")
    @Expose
    private String cert;
    @SerializedName("key")
    @Expose
    private String key;

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getCert() {
        return cert;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
