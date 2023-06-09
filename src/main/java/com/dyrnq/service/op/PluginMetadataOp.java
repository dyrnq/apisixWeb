package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;

public class PluginMetadataOp implements Op, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delPluginMetadata(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        throw new RuntimeException("not support");
    }

    @Override
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getPluginMetadata(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
