package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Certificates {
    @SerializedName("CertificateId")
    @Expose
    private String certificateId;
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
    @SerializedName("CertBeginTime")
    @Expose
    private String certBeginTime;
    @SerializedName("CertEndTime")
    @Expose
    private String certEndTime;

    @SerializedName("SubjectAltName")
    @Expose
    private List<String> subjectAltName;


    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

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

    public String getCertBeginTime() {
        return certBeginTime;
    }

    public void setCertBeginTime(String certBeginTime) {
        this.certBeginTime = certBeginTime;
    }

    public String getCertEndTime() {
        return certEndTime;
    }

    public void setCertEndTime(String certEndTime) {
        this.certEndTime = certEndTime;
    }

    public List<String> getSubjectAltName() {
        return subjectAltName;
    }

    public void setSubjectAltName(List<String> subjectAltName) {
        this.subjectAltName = subjectAltName;
    }
}
