package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// file-logger
public class FileLogger { 

public static final String PLUGIN_NAME = "file-logger";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// log_format
@SerializedName("log_format")
@Expose
public Object logFormat;
// include_resp_body_expr
@SerializedName("include_resp_body_expr")
@Expose
public String[] includeRespBodyExpr;
// path
@SerializedName("path")
@Expose
public String path;
// include_resp_body
@SerializedName("include_resp_body")
@Expose
public boolean includeRespBody;
// match
@SerializedName("match")
@Expose
public String[] match;
// metadata_schema
// private Object logFormat;
}