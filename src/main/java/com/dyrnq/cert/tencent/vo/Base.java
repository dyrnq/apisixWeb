package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Base {
    @SerializedName("Error")
    @Expose
    private Error error;
    @SerializedName("RequestId")
    @Expose
    private String requestId;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
