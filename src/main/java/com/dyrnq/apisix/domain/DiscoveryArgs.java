package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoveryArgs {

    /**
     * namespace id
     */
    @SerializedName("namespace_id")
    @Expose
    private String namespaceId;
    /**
     * group name
     */
    @SerializedName("group_name")
    @Expose
    private String groupName;

    /**
     * namespace id
     */
    public String getNamespaceId() {
        return namespaceId;
    }

    /**
     * namespace id
     */
    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    /**
     * group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
