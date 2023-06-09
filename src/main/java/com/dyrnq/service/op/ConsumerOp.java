package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Consumer;

public class ConsumerOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delConsumer(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Consumer r : client.listConsumers()) {
            client.delConsumer(r.getUsername());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getConsumer(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
