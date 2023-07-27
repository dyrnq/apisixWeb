package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Base {
    
    @SerializedName("RequestId")
    @Expose
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
