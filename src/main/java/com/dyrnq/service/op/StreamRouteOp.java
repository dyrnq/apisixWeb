package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.StreamRoute;

import java.util.List;

public class StreamRouteOp implements Op<StreamRoute>, Sample {

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
    public StreamRoute get(AdminClient client, String id) throws ApisixSDKException {
        return client.getStreamRoute(id);
    }

    @Override
    public List<StreamRoute> list(AdminClient client) throws ApisixSDKException {
        return client.listStreamRoutes();
    }

    @Override
    public String encodeId(StreamRoute obj) {
        return obj.getId();
    }

    @Override
    public Object sample() {
        return null;
    }
}
