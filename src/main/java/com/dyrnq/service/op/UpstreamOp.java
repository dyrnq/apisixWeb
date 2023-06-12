package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Upstream;

import java.util.List;

public class UpstreamOp implements Op<Upstream>, Sample {

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
    public Upstream get(AdminClient client, String id) throws ApisixSDKException {
        return client.getUpstream(id);
    }

    @Override
    public List<Upstream> list(AdminClient client) throws ApisixSDKException {
        return client.listUpstreams();
    }

    @Override
    public Upstream putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        return client.putUpstreamRaw(id, rawData);
    }

    @Override
    public String encodeId(Upstream obj) {
        return obj.getId();
    }

    @Override
    public Object sample() {
        return null;
    }
}
