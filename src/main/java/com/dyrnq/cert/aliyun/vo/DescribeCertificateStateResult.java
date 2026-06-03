package com.dyrnq.cert.aliyun.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DescribeCertificateStateResult {
    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("Domain")
    @Expose
    private String domain;

    @SerializedName("RecordType")
    @Expose
    private String recordType;

    @SerializedName("Certificate")
    @Expose
    private String certificate;

    @SerializedName("RecordDomain")
    @Expose
    private String recordDomain;

    @SerializedName("PrivateKey")
    @Expose
    private String privateKey;

    @SerializedName("ValidateType")
    @Expose
    private String validateType;

    @SerializedName("RecordValue")
    @Expose
    private String recordValue;

    @SerializedName("Content")
    @Expose
    private String content;

    @SerializedName("Uri")
    @Expose
    private String uri;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getRecordDomain() {
        return recordDomain;
    }

    public void setRecordDomain(String recordDomain) {
        this.recordDomain = recordDomain;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getValidateType() {
        return validateType;
    }

    public void setValidateType(String validateType) {
        this.validateType = validateType;
    }

    public String getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(String recordValue) {
        this.recordValue = recordValue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
