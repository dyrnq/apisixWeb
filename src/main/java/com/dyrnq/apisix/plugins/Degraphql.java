package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// degraphql
public class Degraphql { 
// query
@SerializedName("query")
@Expose
public String query;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// variables
@SerializedName("variables")
@Expose
public String[] variables;
// operation_name
@SerializedName("operation_name")
@Expose
public String operationName;
}