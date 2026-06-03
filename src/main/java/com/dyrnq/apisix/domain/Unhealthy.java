package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Unhealthy {

    @SerializedName("http_failures")
    @Expose
    private Integer httpFailures = 5;

    @SerializedName("http_statuses")
    @Expose
    private Set<Integer> httpStatuses = new LinkedHashSet<Integer>(Arrays.asList(429, 500, 503));

    @SerializedName("tcp_failures")
    @Expose
    private Integer tcpFailures = 2;

    @SerializedName("timeouts")
    @Expose
    private Integer timeouts = 7;

    public Integer getHttpFailures() {
        return httpFailures;
    }

    public void setHttpFailures(Integer httpFailures) {
        this.httpFailures = httpFailures;
    }

    public Set<Integer> getHttpStatuses() {
        return httpStatuses;
    }

    public void setHttpStatuses(Set<Integer> httpStatuses) {
        this.httpStatuses = httpStatuses;
    }

    public Integer getTcpFailures() {
        return tcpFailures;
    }

    public void setTcpFailures(Integer tcpFailures) {
        this.tcpFailures = tcpFailures;
    }

    public Integer getTimeouts() {
        return timeouts;
    }

    public void setTimeouts(Integer timeouts) {
        this.timeouts = timeouts;
    }
}
