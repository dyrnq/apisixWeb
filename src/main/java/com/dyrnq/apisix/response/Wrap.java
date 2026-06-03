package com.dyrnq.apisix.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wrap<T> {

    @SerializedName("value")
    @Expose
    private T value;

    @SerializedName("createdIndex")
    @Expose
    private String createdIndex;

    @SerializedName("modifiedIndex")
    @Expose
    private String modifiedIndex;

    @SerializedName("key")
    @Expose
    private String key;

    public String getModifiedIndex() {
        return modifiedIndex;
    }

    public void setModifiedIndex(String modifiedIndex) {
        this.modifiedIndex = modifiedIndex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getCreatedIndex() {
        return createdIndex;
    }

    //	public Item<T> getValue() {
    //		return value;
    //	}
    //
    //	public void setValue(Item<T> value) {
    //		this.value = value;
    //	}

    public void setCreatedIndex(String createdIndex) {
        this.createdIndex = createdIndex;
    }
}
