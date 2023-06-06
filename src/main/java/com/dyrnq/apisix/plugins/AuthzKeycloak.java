package com.dyrnq.apisix.plugins;
import com.google.gson.annotations.Expose; 
import com.google.gson.annotations.SerializedName; 
import java.util.Map; 
// authz-keycloak
public class AuthzKeycloak { 

public static final String PLUGIN_NAME = "authz-keycloak";
// _meta
@SerializedName("_meta")
@Expose
public Meta meta;
// timeout
@SerializedName("timeout")
@Expose
public Integer timeout;
// token_endpoint
@SerializedName("token_endpoint")
@Expose
public String tokenEndpoint;
// keepalive_timeout
@SerializedName("keepalive_timeout")
@Expose
public Integer keepaliveTimeout;
// discovery
@SerializedName("discovery")
@Expose
public String discovery;
// access_token_expires_leeway
@SerializedName("access_token_expires_leeway")
@Expose
public Integer accessTokenExpiresLeeway;
// resource_registration_endpoint
@SerializedName("resource_registration_endpoint")
@Expose
public String resourceRegistrationEndpoint;
// ssl_verify
@SerializedName("ssl_verify")
@Expose
public boolean sslVerify;
// refresh_token_expires_leeway
@SerializedName("refresh_token_expires_leeway")
@Expose
public Integer refreshTokenExpiresLeeway;
// access_denied_redirect_uri
@SerializedName("access_denied_redirect_uri")
@Expose
public String accessDeniedRedirectUri;
// policy_enforcement_mode
@SerializedName("policy_enforcement_mode")
@Expose
public String policyEnforcementMode;
// access_token_expires_in
@SerializedName("access_token_expires_in")
@Expose
public Integer accessTokenExpiresIn;
// keepalive
@SerializedName("keepalive")
@Expose
public boolean keepalive;
// permissions
@SerializedName("permissions")
@Expose
public String[] permissions;
// lazy_load_paths
@SerializedName("lazy_load_paths")
@Expose
public boolean lazyLoadPaths;
// http_method_as_scope
@SerializedName("http_method_as_scope")
@Expose
public boolean httpMethodAsScope;
// cache_ttl_seconds
@SerializedName("cache_ttl_seconds")
@Expose
public Integer cacheTtlSeconds;
// client_secret
@SerializedName("client_secret")
@Expose
public String clientSecret;
// refresh_token_expires_in
@SerializedName("refresh_token_expires_in")
@Expose
public Integer refreshTokenExpiresIn;
// keepalive_pool
@SerializedName("keepalive_pool")
@Expose
public Integer keepalivePool;
// password_grant_token_generation_incoming_uri
@SerializedName("password_grant_token_generation_incoming_uri")
@Expose
public String passwordGrantTokenGenerationIncomingUri;
// client_id
@SerializedName("client_id")
@Expose
public String clientId;
// grant_type
@SerializedName("grant_type")
@Expose
public String grantType;
}