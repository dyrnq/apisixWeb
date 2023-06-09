package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.StreamRoute;

public class StreamRouteOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delStreamRoute(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (StreamRoute r : client.listStreamRoutes()) {
            client.delStreamRoute(r.getId());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getStreamRoute(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
