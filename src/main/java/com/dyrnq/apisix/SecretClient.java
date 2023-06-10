package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.Secret;
import com.dyrnq.apisix.profile.HttpProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecretClient extends BaseClient implements Stub<Secret> {

    public static final String PATH = "/apisix/admin/secrets";

    public SecretClient(Profile profile) {
        super(profile);
    }

    public Secret get(String id) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, PATH + "/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Multi<Secret> query(String page, String page_size) throws ApisixSDKException {
        Multi<Secret> rsp = null;
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE, page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE, page_size);
            Type type = new TypeToken<Multi<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(null, HttpProfile.REQ_GET, PATH, mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }

    public void del(String id) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            this.doRequest(null, HttpProfile.REQ_DELETE, PATH + "/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public List<Secret> list() throws ApisixSDKException {
        Multi<Secret> rsp = null;
        try {
            Type type = new TypeToken<Multi<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, PATH), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        List<Secret> result = this.arrangeMulti(rsp.getNodes());
        return result;
    }

    public Secret put(String id, Secret obj) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(obj, HttpProfile.REQ_PUT, PATH + "/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, PATH + "/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret patchRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PATCH, PATH + "/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret post(Secret obj) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(obj, HttpProfile.REQ_POST, PATH + "/"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

}