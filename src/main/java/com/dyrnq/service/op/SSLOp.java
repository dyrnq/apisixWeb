package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.SSL;
import java.util.ArrayList;
import java.util.List;

public class SSLOp implements Op<SSL>, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delSSL(i);
        }
    }

    @Override
    public SSL put(AdminClient client, SSL obj) throws ApisixSDKException {
        return client.putSSL(obj.getId(), obj);
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (SSL r : client.listSSLs()) {
            client.delSSL(r.getId());
        }
    }

    @Override
    public SSL get(AdminClient client, String id) throws ApisixSDKException {
        return client.getSSL(id);
    }

    @Override
    public SSL putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        return client.putSSLRaw(id, rawData);
    }

    @Override
    public List<SSL> list(AdminClient client, String[] id) throws ApisixSDKException {
        List<SSL> list = new ArrayList<>();
        for (String i : id) {
            list.add(get(client, i));
        }
        return list;
    }

    @Override
    public List<SSL> list(AdminClient client) throws ApisixSDKException {
        return client.listSSLs();
    }

    @Override
    public String encodeId(SSL obj) {
        return obj.getId();
    }

    @Override
    public Object sample() {
        return null;
    }
}
