package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import java.util.List;
import java.util.Map;

public class PluginMetadataOp implements Op<Map>, Sample {

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
    public Map get(AdminClient client, String id) throws ApisixSDKException {
        return client.getPluginMetadata(id);
    }

    @Override
    public List list(AdminClient client) throws ApisixSDKException {
        throw new RuntimeException("not support");
    }

    @Override
    public String encodeId(Map obj) {
        throw new RuntimeException("not support");
    }

    @Override
    public Map putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        throw new RuntimeException("not support");
    }

    @Override
    public List<Map> list(AdminClient client, String[] id) throws ApisixSDKException {
        throw new RuntimeException("not support");
    }

    @Override
    public Map put(AdminClient client, Map obj) throws ApisixSDKException {
        throw new RuntimeException("not support");
    }

    @Override
    public Object sample() {
        return null;
    }
}
