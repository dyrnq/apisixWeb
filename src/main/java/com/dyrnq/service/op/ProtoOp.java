package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Proto;

import java.util.ArrayList;
import java.util.List;

public class ProtoOp implements Op<Proto>, Sample {

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
    public Proto get(AdminClient client, String id) throws ApisixSDKException {
        return client.getProto(id);
    }

    @Override
    public List<Proto> list(AdminClient client) throws ApisixSDKException {
        return client.listProtos();
    }

    @Override
    public String encodeId(Proto obj) {
        return obj.getId();
    }

    @Override
    public Proto putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        return client.putProtoRaw(id, rawData);
    }

    @Override
    public List<Proto> list(AdminClient client, String[] id) throws ApisixSDKException {
        List<Proto> list = new ArrayList<>();
        for (String i : id) {
            list.add(get(client, i));
        }
        return list;
    }

    @Override
    public Object sample() {
        return null;
    }
}
