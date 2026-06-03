package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Credential;
import java.util.ArrayList;
import java.util.List;

public class CredentialOp implements Op<Credential>, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        // id格式: "username/credential_id"
        for (String i : id) {
            String[] parts = i.split("/", 2);
            if (parts.length == 2) {
                client.delCredential(parts[0], parts[1]);
            } else {
                throw new ApisixSDKException("invalid credential id format, expected username/credential_id");
            }
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        // drop all credentials across all consumers
        for (Credential r : client.listAllCredentials()) {
            String username = r.getUsername();
            if (username != null && r.getId() != null) {
                client.delCredential(username, r.getId());
            }
        }
    }

    @Override
    public Credential get(AdminClient client, String id) throws ApisixSDKException {
        String[] parts = id.split("/", 2);
        if (parts.length == 2) {
            return client.getCredential(parts[0], parts[1]);
        }
        throw new ApisixSDKException("invalid credential id format, expected username/credential_id");
    }

    @Override
    public List<Credential> list(AdminClient client) throws ApisixSDKException {
        return client.listAllCredentials();
    }

    @Override
    public List<Credential> list(AdminClient client, String[] id) throws ApisixSDKException {
        List<Credential> result = new ArrayList<>();
        if (id != null) {
            for (String i : id) {
                String[] parts = i.split("/", 2);
                if (parts.length == 2) {
                    Credential obj = client.getCredential(parts[0], parts[1]);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String encodeId(Credential obj) {
        return obj.getUsername() != null ? obj.getUsername() + "/" + obj.getId() : obj.getId();
    }

    @Override
    public Credential putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        // id格式: "username/credential_id"
        String[] parts = id.split("/", 2);
        if (parts.length == 2) {
            return client.putCredentialRaw(parts[0], parts[1], rawData);
        }
        throw new ApisixSDKException("invalid credential id format, expected username/credential_id");
    }

    @Override
    public Credential put(AdminClient client, Credential obj) throws ApisixSDKException {
        String username = obj.getUsername();
        if (username == null) {
            throw new ApisixSDKException("username is required for credential");
        }
        return client.putCredential(username, obj.getId(), obj);
    }

    @Override
    public Object sample() {
        Credential c = new Credential();
        c.setId("credential-sample");
        c.setUsername("consumer-username");
        return c;
    }
}
