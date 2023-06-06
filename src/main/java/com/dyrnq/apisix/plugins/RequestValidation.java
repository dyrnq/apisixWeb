package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// request-validation
public class RequestValidation { 

public static final String PLUGIN_NAME = "request-validation";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// rejected_code
@SerializedName("rejected_code")
@Expose
public Integer rejectedCode;
// rejected_msg
@SerializedName("rejected_msg")
@Expose
public String rejectedMsg;
// body_schema
@SerializedName("body_schema")
@Expose
public Object bodySchema;
// header_schema
@SerializedName("header_schema")
@Expose
public Object headerSchema;
}