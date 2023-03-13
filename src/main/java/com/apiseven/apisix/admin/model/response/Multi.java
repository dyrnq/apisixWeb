package com.apiseven.apisix.admin.model.response;

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
	private int total;

	public void setTotal(int total){
		this.total=total;
	}
	public int getTotal(){
		return this.total;
	}



}
