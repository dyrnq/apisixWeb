package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Proto;

public class ProtoOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delProto(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Proto r : client.listProtos()) {
            client.delProto(r.getId());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getProto(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
