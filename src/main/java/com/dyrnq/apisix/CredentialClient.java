package com.dyrnq.apisix;

import com.dyrnq.apisix.domain.Credential;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * APISIX Credential API client.
 *
 * <p>Credentials are sub-resources of Consumers. API path:
 * /apisix/admin/consumers/{username}/credentials/{credential_id}
 */
public class CredentialClient extends BaseClient {

    private final String username;

    public CredentialClient(Profile profile, String username) {
        super(profile);
        this.username = username;
    }

    private String path() {
        return "/apisix/admin/consumers/" + username + "/credentials";
    }

    private String path(String id) {
        return "/apisix/admin/consumers/" + username + "/credentials/" + id;
    }

    public Credential get(String id) throws ApisixSDKException {
        Wrap<Credential> rsp;
        try {
            Type type = new TypeToken<Wrap<Credential>>() {}.getType();
            rsp = gson.fromJson(this.doRequest(HttpMethod.REQ_GET, path(id)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) throw e;
            else throw new ApisixSDKException(e.getMessage());
        }
        return rsp.getValue();
    }

    public void del(String id) throws ApisixSDKException {
        try {
            this.doRequest(null, HttpMethod.REQ_DELETE, path(id));
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) throw e;
            else throw new ApisixSDKException(e.getMessage());
        }
    }

    public List<Credential> list() throws ApisixSDKException {
        Multi<Credential> rsp;
        try {
            Type type = new TypeToken<Multi<Credential>>() {}.getType();
            rsp = gson.fromJson(this.doRequest(HttpMethod.REQ_GET, path()), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) throw e;
            else throw new ApisixSDKException(e.getMessage());
        }
        return this.arrangeMulti(rsp.getNodes());
    }

    public Credential put(String id, Credential obj) throws ApisixSDKException {
        Wrap<Credential> rsp;
        try {
            Type type = new TypeToken<Wrap<Credential>>() {}.getType();
            rsp = gson.fromJson(this.doRequest(obj, HttpMethod.REQ_PUT, path(id)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) throw e;
            else throw new ApisixSDKException(e.getMessage());
        }
        return rsp.getValue();
    }

    public Credential putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<Credential> rsp;
        try {
            Type type = new TypeToken<Wrap<Credential>>() {}.getType();
            rsp = gson.fromJson(this.doRequest(null, HttpMethod.REQ_PUT, path(id), rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) throw e;
            else throw new ApisixSDKException(e.getMessage());
        }
        return rsp.getValue();
    }

    /** 遍历所有consumer，汇总所有credentials */
    public static List<Credential> listAll(Profile profile) throws ApisixSDKException {
        List<Credential> all = new ArrayList<>();
        try {
            // 先获取所有consumer
            com.dyrnq.apisix.domain.Consumer consumerPlaceholder = new com.dyrnq.apisix.domain.Consumer();
            com.dyrnq.apisix.ConsumerClient consumerClient = new com.dyrnq.apisix.ConsumerClient(profile);
            List<com.dyrnq.apisix.domain.Consumer> consumers = consumerClient.list();
            if (consumers != null) {
                for (com.dyrnq.apisix.domain.Consumer c : consumers) {
                    String username = c.getUsername();
                    if (username != null) {
                        try {
                            CredentialClient cc = new CredentialClient(profile, username);
                            List<Credential> creds = cc.list();
                            if (creds != null) {
                                for (Credential cred : creds) {
                                    cred.setUsername(username);
                                }
                                all.addAll(creds);
                            }
                        } catch (Exception ignore) {
                            // 该consumer可能没有credentials
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApisixSDKException(e.getMessage());
        }
        return all;
    }
}
