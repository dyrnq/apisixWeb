package com.dyrnq.apisix.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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