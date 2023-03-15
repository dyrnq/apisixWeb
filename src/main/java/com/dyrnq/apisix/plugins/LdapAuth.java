package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// ldap-auth
public class LdapAuth { 
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// base_dn
@SerializedName("base_dn")
@Expose
public String baseDn;
// ldap_uri
@SerializedName("ldap_uri")
@Expose
public String ldapUri;
// uid
@SerializedName("uid")
@Expose
public String uid;
// tls_verify
@SerializedName("tls_verify")
@Expose
public boolean tlsVerify;
// use_tls
@SerializedName("use_tls")
@Expose
public boolean useTls;
// user_dn
@SerializedName("user_dn")
@Expose
public String userDn;
}