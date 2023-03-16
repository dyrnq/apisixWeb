package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// example-plugin
public class ExamplePlugin { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// s
@SerializedName("s")
@Expose
public String s;
// ip
@SerializedName("ip")
@Expose
public String ip;
// port
@SerializedName("port")
@Expose
public Integer port;
// i
@SerializedName("i")
@Expose
public Number i;
// t
@SerializedName("t")
@Expose
public String[] t;
// metadata_schema
// private String skey;
// metadata_schema
// private Number ikey;
}