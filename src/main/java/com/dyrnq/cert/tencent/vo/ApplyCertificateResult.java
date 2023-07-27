package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyCertificateResult extends Base {
    @SerializedName("CertificateId")
    @Expose
    private String certificateId;

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
}
