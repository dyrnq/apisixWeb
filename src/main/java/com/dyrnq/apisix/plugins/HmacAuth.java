package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// hmac-auth
public class HmacAuth { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// consumer_schema
// secret_key
@SerializedName("secret_key")
@Expose
public String secretKey;
// consumer_schema
// clock_skew
@SerializedName("clock_skew")
@Expose
public Integer clockSkew;
// consumer_schema
// keep_headers
@SerializedName("keep_headers")
@Expose
public boolean keepHeaders;
// consumer_schema
// encode_uri_params
@SerializedName("encode_uri_params")
@Expose
public boolean encodeUriParams;
// consumer_schema
// validate_request_body
@SerializedName("validate_request_body")
@Expose
public boolean validateRequestBody;
// consumer_schema
// max_req_body
@SerializedName("max_req_body")
@Expose
public Integer maxReqBody;
// consumer_schema
// signed_headers
@SerializedName("signed_headers")
@Expose
public String[] signedHeaders;
// consumer_schema
// algorithm
@SerializedName("algorithm")
@Expose
public String algorithm;
// consumer_schema
// access_key
@SerializedName("access_key")
@Expose
public String accessKey;
}