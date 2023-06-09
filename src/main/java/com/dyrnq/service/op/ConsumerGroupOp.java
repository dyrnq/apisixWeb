package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.ConsumerGroup;

public class ConsumerGroupOp implements Op, Sample {
    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delConsumerGroup(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (ConsumerGroup r : client.listConsumerGroups()) {
            client.delConsumerGroup(r.getId());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getConsumerGroup(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
