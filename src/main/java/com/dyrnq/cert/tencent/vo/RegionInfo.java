package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionInfo {
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("RegionName")
    @Expose
    private String regionName;
    @SerializedName("RegionState")
    @Expose
    private String regionState;


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionState() {
        return regionState;
    }

    public void setRegionState(String regionState) {
        this.regionState = regionState;
    }
}
