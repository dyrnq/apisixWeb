package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// cors
public class Cors { 

public static final String PLUGIN_NAME = "cors";
// allow_headers
@SerializedName("allow_headers")
@Expose
public String allowHeaders;
// expose_headers
@SerializedName("expose_headers")
@Expose
public String exposeHeaders;
// max_age
@SerializedName("max_age")
@Expose
public Integer maxAge;
// allow_credential
@SerializedName("allow_credential")
@Expose
public boolean allowCredential;
// allow_origins
@SerializedName("allow_origins")
@Expose
public String allowOrigins;
// allow_origins_by_regex
@SerializedName("allow_origins_by_regex")
@Expose
public String[] allowOriginsByRegex;
// allow_methods
@SerializedName("allow_methods")
@Expose
public String allowMethods;
// allow_origins_by_metadata
@SerializedName("allow_origins_by_metadata")
@Expose
public String[] allowOriginsByMetadata;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// metadata_schema
// private Object allowOrigins;
}