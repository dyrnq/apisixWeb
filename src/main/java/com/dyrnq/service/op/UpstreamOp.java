package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Upstream;

public class UpstreamOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delUpstream(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Upstream r : client.listUpstreams()) {
            client.delUpstream(r.getId());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getUpstream(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
