package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tls {
    public Tls() {
        super();
    }

    @SerializedName("client_cert")
    @Expose
    private String clientCert;

    public void setClientCert(String clientCert) {
        this.clientCert = clientCert;
    }

    public String getClientCert() {
        return this.clientCert;
    }

    @SerializedName("client_key")
    @Expose
    private String clientKey;

    @SerializedName("verify")
    @Expose
    private Boolean verify;

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getClientKey() {
        return this.clientKey;
    }

    @SerializedName("client_cert_id")
    @Expose
    private String clientCertId;

    public void setClientCertId(String clientCertId) {
        this.clientCertId = clientCertId;
    }

    public String getClientCertId() {
        return this.clientCertId;
    }

    public Tls(String clientCert, String clientKey) {
        this.clientCert = clientCert;
        this.clientKey = clientKey;
    }

    /**
     * Turn on server certificate verification, currently only kafka upstream is supported
     *
     */
    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }
}
