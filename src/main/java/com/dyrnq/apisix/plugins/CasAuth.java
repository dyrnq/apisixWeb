package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// cas-auth
public class CasAuth { 
// cas_callback_uri
@SerializedName("cas_callback_uri")
@Expose
public String casCallbackUri;
// logout_uri
@SerializedName("logout_uri")
@Expose
public String logoutUri;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// idp_uri
@SerializedName("idp_uri")
@Expose
public String idpUri;
}