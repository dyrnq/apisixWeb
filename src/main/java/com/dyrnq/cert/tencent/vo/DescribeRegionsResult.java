package com.dyrnq.cert.tencent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DescribeRegionsResult extends Base {

    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;

    @SerializedName("RegionSet")
    @Expose
    private List<RegionInfo> regionSet;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<RegionInfo> getRegionSet() {
        return regionSet;
    }

    public void setRegionSet(List<RegionInfo> regionSet) {
        this.regionSet = regionSet;
    }
}
