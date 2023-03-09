package com.apiseven.apisix.admin.model.response;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Item<T> {
    @SerializedName("value")
    @Expose
    private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}


}