package com.dyrnq.apisix.profile;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DefaultProfile implements Profile {
    private String endpoint;
    private Credential credential;
    private Logger logger;
    private String version;
    private HttpProfile httpProfile;
    private  Endpoint currentEndpoint;
    private List<Endpoint> endpoints = new ArrayList<>();

    private DefaultProfile(String endpoint, String version, Credential credential) {
        this.credential = credential;
        this.endpoint   = endpoint;
        this.version = version;
        this.httpProfile = new HttpProfile();

        String[] domains = endpoint.split(",");
        Endpoint ep;
        for (String domain : domains){
            ep = new Endpoint();
            ep.setDomain(domain);
            this.endpoints.add(ep);
            if(this.currentEndpoint == null){
                this.currentEndpoint = ep;
            }
        }
    }

    public static synchronized DefaultProfile getProfile(String endpoint, String version, Credential credential) {
        DefaultProfile profile = new DefaultProfile(endpoint, version, credential);
        return profile;
    }

    public synchronized Credential getCredential() {
        return this.credential;
    }

    public void setCurrentEndpoint(Endpoint ep){
        this.currentEndpoint = ep;
    }

    public Endpoint getCurrentEndpoint(){
        return this.currentEndpoint;
    }

    public List<Endpoint> getEndpoints(){
        return this.endpoints;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getVersion(){
        return this.version;
    }

    public String getEndpoint(){
        return this.endpoint;
    }

    public HttpProfile getHttpProfile(){
        return this.httpProfile;
    }

}
