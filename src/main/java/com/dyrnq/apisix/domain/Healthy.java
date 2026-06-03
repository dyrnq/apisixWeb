package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Healthy {

    @SerializedName("successes")
    @Expose
    private Integer successes = 5;

    @SerializedName("http_statuses")
    @Expose
    private Set<Integer> httpStatuses = new LinkedHashSet<Integer>(Arrays.asList(
            200, 201, 202, 203, 204, 205, 206, 207, 208, 226, 300, 301, 302, 303, 304, 305, 306, 307, 308));

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
}
