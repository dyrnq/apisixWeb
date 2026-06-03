package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyCertificateArg {
    @SerializedName("DvAuthMethod")
    @Expose
    private String dvAuthMethod;

    @SerializedName("DomainName")
    @Expose
    private String domainName;

    @SerializedName("ProjectId")
    @Expose
    private Integer projectId;

    @SerializedName("PackageType")
    @Expose
    private String packageType;

    @SerializedName("ContactEmail")
    @Expose
    private String contactEmail;

    @SerializedName("ContactPhone")
    @Expose
    private String contactPhone;

    @SerializedName("ValidityPeriod")
    @Expose
    private String validityPeriod;

    @SerializedName("CsrEncryptAlgo")
    @Expose
    private String csrEncryptAlgo;

    @SerializedName("Alias")
    @Expose
    private String alias;

    @SerializedName("OldCertificateId")
    @Expose
    private String oldCertificateId;

    @SerializedName("PackageId")
    @Expose
    private String packageId;

    @SerializedName("DeleteDnsAutoRecord")
    @Expose
    private Boolean deleteDnsAutoRecord;

    public String getDvAuthMethod() {
        return dvAuthMethod;
    }

    public void setDvAuthMethod(String dvAuthMethod) {
        this.dvAuthMethod = dvAuthMethod;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getCsrEncryptAlgo() {
        return csrEncryptAlgo;
    }

    public void setCsrEncryptAlgo(String csrEncryptAlgo) {
        this.csrEncryptAlgo = csrEncryptAlgo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOldCertificateId() {
        return oldCertificateId;
    }

    public void setOldCertificateId(String oldCertificateId) {
        this.oldCertificateId = oldCertificateId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Boolean getDeleteDnsAutoRecord() {
        return deleteDnsAutoRecord;
    }

    public void setDeleteDnsAutoRecord(Boolean deleteDnsAutoRecord) {
        this.deleteDnsAutoRecord = deleteDnsAutoRecord;
    }
}
