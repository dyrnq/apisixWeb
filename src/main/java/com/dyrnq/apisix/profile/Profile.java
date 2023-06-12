package com.dyrnq.apisix.profile;

//import org.slf4j.Logger;

import java.util.List;

public interface Profile {
    Credential getCredential();

    Timeout timeout();

//    Logger getLogger();

//    void setLogger(Logger logger);

    String getVersion();

    String getEndpoint();

    Endpoint getCurrentEndpoint();

    void setCurrentEndpoint(Endpoint ep);

    List<Endpoint> getEndpoints();

    //void setEndpoints(List<Endpoint> endpoints);
}
