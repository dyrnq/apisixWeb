package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// kafka-proxy
public class KafkaProxy { 

public static final String PLUGIN_NAME = "kafka-proxy";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// sasl
@SerializedName("sasl")
@Expose
public Object sasl;
}