package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.SSL;

public class SSLOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delSSL(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (SSL r : client.listSSLs()) {
            client.delSSL(r.getId());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getSSL(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
