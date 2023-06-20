package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Secret;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SecretOp implements Op<Secret>, Sample {

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
    public Secret get(AdminClient client, String id) throws ApisixSDKException {
        return client.getSecret(id);
    }

    @Override
    public List<Secret> list(AdminClient client) throws ApisixSDKException {
        return client.listSecrets();
    }

    @Override
    public Secret putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        return client.putSecretRaw(id, rawData);
    }

    @Override
    public List<Secret> list(AdminClient client, String[] id) throws ApisixSDKException {
        List<Secret> list = new ArrayList<>();
        for (String i : id) {
            list.add(get(client, i));
        }
        return list;
    }

    @Override
    public String encodeId(Secret obj) {
        try {
            return URLEncoder.encode(obj.getId(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object sample() {
        return null;
    }
}
