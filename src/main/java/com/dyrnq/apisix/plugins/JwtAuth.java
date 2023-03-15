package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// jwt-auth
public class JwtAuth { 
// query
@SerializedName("query")
@Expose
public String query;
// cookie
@SerializedName("cookie")
@Expose
public String cookie;
// hide_credentials
@SerializedName("hide_credentials")
@Expose
public boolean hideCredentials;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// header
@SerializedName("header")
@Expose
public String header;
// exp
@SerializedName("exp")
@Expose
public Integer exp;
// secret
@SerializedName("secret")
@Expose
public String secret;
// base64_secret
@SerializedName("base64_secret")
@Expose
public boolean base64Secret;
// key
@SerializedName("key")
@Expose
public String key;
// lifetime_grace_period
@SerializedName("lifetime_grace_period")
@Expose
public Integer lifetimeGracePeriod;
// algorithm
@SerializedName("algorithm")
@Expose
public String algorithm;
}