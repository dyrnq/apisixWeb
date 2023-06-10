package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.Consumer;
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

public class ConsumerClient extends BaseClient implements Stub<Consumer> {

    public static final String PATH = "/apisix/admin/consumers";

    public ConsumerClient(Profile profile) {
        super(profile);
    }

    public Consumer get(String id) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>() {
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

    public Multi<Consumer> query(String page, String page_size) throws ApisixSDKException {
        Multi<Consumer> rsp = null;
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE, page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE, page_size);
            Type type = new TypeToken<Multi<Consumer>>() {
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
        Wrap<Consumer> rsp = null;
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

    public List<Consumer> list() throws ApisixSDKException {
        Multi<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Multi<Consumer>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, PATH), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        List<Consumer> result = this.arrangeMulti(rsp.getNodes());
        return result;
    }

    public Consumer put(String id, Consumer obj) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>() {
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

    public Consumer putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>() {
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

    public Consumer patchRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>() {
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

    public Consumer post(Consumer obj) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>() {
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