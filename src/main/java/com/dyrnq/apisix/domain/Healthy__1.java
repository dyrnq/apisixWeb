package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Healthy__1 {

    @SerializedName("successes")
    @Expose
    private Integer successes = 2;
    @SerializedName("http_statuses")
    @Expose
    private Set<Integer> httpStatuses = new LinkedHashSet<Integer>(Arrays.asList(200, 302));
    @SerializedName("interval")
    @Expose
    private Integer interval = 1;

    public Integer getSuccesses() {
        return successes;
    }

    public void setSuccesses(Integer successes) {
        this.successes = successes;
    }

    public Set<Integer> getHttpStatuses() {
        return httpStatuses;
    }

    public void setHttpStatuses(Set<Integer> httpStatuses) {
        this.httpStatuses = httpStatuses;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

}
