package com.dyrnq.cert.aliyun.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CertificateOrder {
    @SerializedName("AliyunOrderId")
    @Expose
    private Long aliyunOrderId;

    @SerializedName("CertEndTime")
    @Expose
    private Long certEndTime;

    @SerializedName("CertStartTime")
    @Expose
    private Long certStartTime;

    @SerializedName("OrderId")
    @Expose
    private Long orderId;

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("StartDate")
    @Expose
    private String startDate;

    @SerializedName("EndDate")
    @Expose
    private String endDate;

    @SerializedName("Domain")
    @Expose
    private String domain;

    public Long getAliyunOrderId() {
        return aliyunOrderId;
    }

    public void setAliyunOrderId(Long aliyunOrderId) {
        this.aliyunOrderId = aliyunOrderId;
    }

    public Long getCertEndTime() {
        return certEndTime;
    }

    public void setCertEndTime(Long certEndTime) {
        this.certEndTime = certEndTime;
    }

    public Long getCertStartTime() {
        return certStartTime;
    }

    public void setCertStartTime(Long certStartTime) {
        this.certStartTime = certStartTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
