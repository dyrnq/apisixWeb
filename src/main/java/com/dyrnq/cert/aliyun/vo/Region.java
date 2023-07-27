package com.dyrnq.cert.aliyun.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Region {
    @SerializedName("RegionId")
    @Expose
    private String regionId;
    @SerializedName("LocalName")
    @Expose
    private String localName;


    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }
}
