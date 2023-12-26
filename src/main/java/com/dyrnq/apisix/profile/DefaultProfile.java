package com.dyrnq.apisix.profile;

//import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DefaultProfile implements Profile {
    private final String endpoint;
    private final Credential credential;
    private final String version;
    private final Timeout timeOut;
    //    private Logger logger;
    private Endpoint currentEndpoint;
    private final List<Endpoint> endpoints = new ArrayList<>();

    private DefaultProfile(String endpoint, String version, Credential credential) {
        this.credential = credential;
        this.endpoint = endpoint;
        this.version = version;
        this.timeOut = new Timeout(10, 10, 5);

        String[] address = endpoint.split("[,|]");
        Endpoint ep;
        for (String ad : address) {
            ep = new Endpoint();

            if (ad.matches("(?i)^http.*")) {
                ep.setAddress(ad);
            } else {
                ep.setAddress("http://" + ad);
            }
            this.endpoints.add(ep);
            if (this.currentEndpoint == null) {
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

    public Endpoint getCurrentEndpoint() {
        return this.currentEndpoint;
    }

    public void setCurrentEndpoint(Endpoint ep) {
        this.currentEndpoint = ep;
    }

    public List<Endpoint> getEndpoints() {
        return this.endpoints;
    }

//    public void setEndpoints(List<Endpoint> endpoints) {
//        this.endpoints = endpoints;
//    }

//    public Logger getLogger() {
//        return this.logger;
//    }
//
//    public void setLogger(Logger logger) {
//        this.logger = logger;
//    }

    public String getVersion() {
        return this.version;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public Timeout timeout() {
        return this.timeOut;
    }

}
