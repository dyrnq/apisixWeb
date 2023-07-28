package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DescribeCertificateResult extends Base {
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("StatusName")
    @Expose
    private String statusName;

    @SerializedName("StatusMsg")
    @Expose
    private String statusMsg;

    @SerializedName("VerifyType")
    @Expose
    private String verifyType;

    @SerializedName("CertificateId")
    @Expose
    private String certificateId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
}
