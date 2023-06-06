package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// mocking
public class Mocking { 

public static final String PLUGIN_NAME = "mocking";
// delay
@SerializedName("delay")
@Expose
public Integer delay;
// content_type
@SerializedName("content_type")
@Expose
public String contentType;
// with_mock_header
@SerializedName("with_mock_header")
@Expose
public boolean withMockHeader;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// response_schema
@SerializedName("response_schema")
@Expose
public Object responseSchema;
// response_status
@SerializedName("response_status")
@Expose
public Integer responseStatus;
// response_example
@SerializedName("response_example")
@Expose
public String responseExample;
}