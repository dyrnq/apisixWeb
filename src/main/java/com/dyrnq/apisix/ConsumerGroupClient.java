package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.ConsumerGroup;
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

public class ConsumerGroupClient extends BaseClient implements Stub<ConsumerGroup> {

    public static final String PATH = "/apisix/admin/consumer_groups";

    public ConsumerGroupClient(Profile profile) {
        super(profile);
    }

    public ConsumerGroup get(String id) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>() {
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

    public Multi<ConsumerGroup> query(String page, String page_size) throws ApisixSDKException {
        Multi<ConsumerGroup> rsp = null;
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE, page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE, page_size);
            Type type = new TypeToken<Multi<ConsumerGroup>>() {
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
        Wrap<ConsumerGroup> rsp = null;
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

    public List<ConsumerGroup> list() throws ApisixSDKException {
        Multi<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Multi<ConsumerGroup>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, PATH), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        List<ConsumerGroup> result = this.arrangeMulti(rsp.getNodes());
        return result;
    }

    public ConsumerGroup put(String id, ConsumerGroup obj) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>() {
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

    public ConsumerGroup putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>() {
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

    public ConsumerGroup patchRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>() {
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

    public ConsumerGroup post(ConsumerGroup obj) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>() {
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