package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * https://cloud.tencent.com/document/api/400/41671
 */
public class DescribeCertificatesArg {

    @SerializedName("Offset")
    @Expose
    private Integer offset; // 分页偏移量，从0开始。

    @SerializedName("SearchKey")
    @Expose
    private String searchKey; // 搜索关键词，可搜索证书 ID、备注名称、域名。例如： a8xHcaIs。

    @SerializedName("CertificateStatus")
    @Expose
    private Integer[] certificateStatus;

    @SerializedName("Limit")
    @Expose
    private Integer limit; // 每页数量，默认20。最大1000

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer[] getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(Integer[] certificateStatus) {
        this.certificateStatus = certificateStatus;
    }
}
