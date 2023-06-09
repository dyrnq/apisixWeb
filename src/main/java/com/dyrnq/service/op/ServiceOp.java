package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Service;

public class ServiceOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delService(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Service r : client.listServices()) {
            client.delService(r.getId());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getService(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
