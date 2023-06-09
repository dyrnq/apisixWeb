package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Secret;

public class SecretOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delSecret(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Secret r : client.listSecrets()) {
            client.delSecret(r.getId());
        }
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getSecret(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
