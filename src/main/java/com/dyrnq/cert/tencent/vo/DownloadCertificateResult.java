package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadCertificateResult extends Base {
    @SerializedName("Content")
    @Expose
    private String content;

    @SerializedName("ContentType")
    @Expose
    private String contentType;


    /**
     * ZIP base64 编码内容，base64 解码后可保存为 ZIP 文件。
     * 注意：此字段可能返回 null，表示取不到有效值。
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * MIME 类型：application/zip = ZIP 压缩文件。
     * 注意：此字段可能返回 null，表示取不到有效值。
     */
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
