package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// key-auth
public class KeyAuth { 
// query
@SerializedName("query")
@Expose
public String query;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// header
@SerializedName("header")
@Expose
public String header;
// hide_credentials
@SerializedName("hide_credentials")
@Expose
public boolean hideCredentials;
// consumer_schema
// key
@SerializedName("key")
@Expose
public String key;
}