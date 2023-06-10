package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.ConsumerGroup;

import java.util.List;

public class ConsumerGroupOp implements Op<ConsumerGroup>, Sample {
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
    public ConsumerGroup get(AdminClient client, String id) throws ApisixSDKException {
        return client.getConsumerGroup(id);
    }

    @Override
    public List<ConsumerGroup> list(AdminClient client) throws ApisixSDKException {
        return client.listConsumerGroups();
    }

    @Override
    public String encodeId(ConsumerGroup obj) {
        return obj.getId();
    }

    @Override
    public Object sample() {
        return null;
    }
}
