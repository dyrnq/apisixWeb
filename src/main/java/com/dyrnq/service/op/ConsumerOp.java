package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Consumer;

import java.util.List;

public class ConsumerOp implements Op<Consumer>, Sample {

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
    public Consumer get(AdminClient client, String id) throws ApisixSDKException {
        return client.getConsumer(id);
    }

    @Override
    public List<Consumer> list(AdminClient client) throws ApisixSDKException {
        return client.listConsumers();
    }

    @Override
    public String encodeId(Consumer obj) {
        return obj.getUsername();
    }

    @Override
    public Consumer putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        return client.putConsumerRaw(id, rawData);
    }

    @Override
    public Object sample() {
        return null;
    }
}
