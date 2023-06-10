package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.PluginConfig;

import java.util.List;

public class PluginConfigOp implements Op<PluginConfig>, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delPluginConfig(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (PluginConfig r : client.listPluginConfigs()) {
            client.delPluginConfig(r.getId());
        }
    }

    @Override
    public PluginConfig get(AdminClient client, String id) throws ApisixSDKException {
        return client.getPluginConfig(id);
    }

    @Override
    public List<PluginConfig> list(AdminClient client) throws ApisixSDKException {
        return client.listPluginConfigs();
    }

    @Override
    public String encodeId(PluginConfig obj) {
        return obj.getId();
    }

    @Override
    public Object sample() {
        return null;
    }
}
