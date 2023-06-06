package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// openid-connect
public class OpenidConnect { 

public static final String PLUGIN_NAME = "openid-connect";
// bearer_only
@SerializedName("bearer_only")
@Expose
public boolean bearerOnly;
// realm
@SerializedName("realm")
@Expose
public String realm;
// logout_path
@SerializedName("logout_path")
@Expose
public String logoutPath;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// post_logout_redirect_uri
@SerializedName("post_logout_redirect_uri")
@Expose
public String postLogoutRedirectUri;
// public_key
@SerializedName("public_key")
@Expose
public String publicKey;
// discovery
@SerializedName("discovery")
@Expose
public String discovery;
// token_signing_alg_values_expected
@SerializedName("token_signing_alg_values_expected")
@Expose
public String tokenSigningAlgValuesExpected;
// use_pkce
@SerializedName("use_pkce")
@Expose
public boolean usePkce;
// scope
@SerializedName("scope")
@Expose
public String scope;
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// set_refresh_token_header
@SerializedName("set_refresh_token_header")
@Expose
public boolean setRefreshTokenHeader;
// access_token_in_authorization_header
@SerializedName("access_token_in_authorization_header")
@Expose
public boolean accessTokenInAuthorizationHeader;
// set_userinfo_header
@SerializedName("set_userinfo_header")
@Expose
public boolean setUserinfoHeader;
// set_access_token_header
@SerializedName("set_access_token_header")
@Expose
public boolean setAccessTokenHeader;
// set_id_token_header
@SerializedName("set_id_token_header")
@Expose
public boolean setIdTokenHeader;
// client_id
@SerializedName("client_id")
@Expose
public String clientId;
// unauth_action
@SerializedName("unauth_action")
@Expose
public String unauthAction;
// client_secret
@SerializedName("client_secret")
@Expose
public String clientSecret;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// session
@SerializedName("session")
@Expose
public Object session;
// introspection_endpoint
@SerializedName("introspection_endpoint")
@Expose
public String introspectionEndpoint;
// introspection_endpoint_auth_method
@SerializedName("introspection_endpoint_auth_method")
@Expose
public String introspectionEndpointAuthMethod;
// redirect_uri
@SerializedName("redirect_uri")
@Expose
public String redirectUri;
}