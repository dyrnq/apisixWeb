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
// secret_key
@SerializedName("secret_key")
@Expose
public String secretKey;
// clock_skew
@SerializedName("clock_skew")
@Expose
public Integer clockSkew;
// keep_headers
@SerializedName("keep_headers")
@Expose
public boolean keepHeaders;
// encode_uri_params
@SerializedName("encode_uri_params")
@Expose
public boolean encodeUriParams;
// validate_request_body
@SerializedName("validate_request_body")
@Expose
public boolean validateRequestBody;
// max_req_body
@SerializedName("max_req_body")
@Expose
public Integer maxReqBody;
// signed_headers
@SerializedName("signed_headers")
@Expose
public String[] signedHeaders;
// algorithm
@SerializedName("algorithm")
@Expose
public String algorithm;
// access_key
@SerializedName("access_key")
@Expose
public String accessKey;
}