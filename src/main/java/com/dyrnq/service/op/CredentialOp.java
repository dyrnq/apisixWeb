package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Credential;
import java.util.ArrayList;
import java.util.List;

public class CredentialOp implements Op<Credential>, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delCredential(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Credential r : client.listCredentials()) {
            client.delCredential(r.getId());
        }
    }

    @Override
    public Credential get(AdminClient client, String id) throws ApisixSDKException {
        return client.getCredential(id);
    }

    @Override
    public List<Credential> list(AdminClient client) throws ApisixSDKException {
        return client.listCredentials();
    }

    @Override
    public List<Credential> list(AdminClient client, String[] id) throws ApisixSDKException {
        List<Credential> result = new ArrayList<>();
        if (id != null) {
            for (String i : id) {
                Credential obj = client.getCredential(i);
                if (obj != null) {
                    result.add(obj);
                }
            }
        }
        return result;
    }

    @Override
    public String encodeId(Credential obj) {
        return obj.getId();
    }

    @Override
    public Credential putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        return client.putCredentialRaw(id, rawData);
    }

    @Override
    public Credential put(AdminClient client, Credential obj) throws ApisixSDKException {
        return client.putCredential(obj.getId(), obj);
    }

    @Override
    public Object sample() {
        Credential c = new Credential();
        c.setId("credential-sample");
        return c;
    }
}
