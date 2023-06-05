package com.dyrnq.apisix.profile;

import org.slf4j.Logger;

import java.util.List;

public interface Profile {
    Credential getCredential();
    HttpProfile getHttpProfile();
    Logger getLogger();
    void setLogger(Logger logger);
    String getVersion();
    String getEndpoint();
    public void setCurrentEndpoint(Endpoint ep);
    public Endpoint getCurrentEndpoint();
    public List<Endpoint> getEndpoints();
    public void setEndpoints(List<Endpoint> endpoints);
}
