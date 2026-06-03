package com.dyrnq.apisix.plugins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// grpc-transcode

public class GrpcTranscode {

    public static final String PLUGIN_NAME = "grpc-transcode";
    // method
    @SerializedName("method")
    @Expose
    public String method;
    // proto_id
    @SerializedName("proto_id")
    @Expose
    public Object protoId;
    // _meta
    @SerializedName("_meta")
    @Expose
    public Meta meta;
    // status_detail_type
    @SerializedName("status_detail_type")
    @Expose
    public String statusDetailType;
    // deadline
    @SerializedName("deadline")
    @Expose
    public Number deadline;
    // service
    @SerializedName("service")
    @Expose
    public String service;
    // pb_option
    @SerializedName("pb_option")
    @Expose
    public String[] pbOption;
    // show_status_in_body
    @SerializedName("show_status_in_body")
    @Expose
    public boolean showStatusInBody;
}
