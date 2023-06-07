package com.dyrnq.apisix.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Multi<T> {
	@SerializedName("list")
	@Expose
    private List<Item<T>> nodes;

	public List<Item<T>> getNodes() {
		return nodes;
	}

	public void setNodes(List<Item<T>> nodes) {
		this.nodes = nodes;
	}
	@SerializedName("total")
	@Expose
	private Number total;

	public void setTotal(Number total){
		this.total=total;
	}
	public Number getTotal(){
		return this.total;
	}



}
