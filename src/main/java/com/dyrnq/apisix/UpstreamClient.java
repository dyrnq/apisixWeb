package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.Upstream;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpstreamClient extends BaseClient implements Stub<Upstream> {

    public static final String PATH = "/apisix/admin/upstreams";

    public UpstreamClient(Profile profile) {
        super(profile);
    }

    public Upstream get(String id) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpMethod.REQ_GET, PATH + "/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Multi<Upstream> query(String page, String page_size) throws ApisixSDKException {
        Multi<Upstream> rsp = null;
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE, page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE, page_size);
            Type type = new TypeToken<Multi<Upstream>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(null, HttpMethod.REQ_GET, PATH, mapToQueryString(paramsMap)), type);
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
        Wrap<Upstream> rsp = null;
        try {
            this.doRequest(null, HttpMethod.REQ_DELETE, PATH + "/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public List<Upstream> list() throws ApisixSDKException {
        Multi<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Multi<Upstream>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpMethod.REQ_GET, PATH), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        List<Upstream> result = this.arrangeMulti(rsp.getNodes());
        return result;
    }

    public Upstream put(String id, Upstream obj) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(obj, HttpMethod.REQ_PUT, PATH + "/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Upstream putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(null, HttpMethod.REQ_PUT, PATH + "/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Upstream patchRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(null, HttpMethod.REQ_PATCH, PATH + "/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Upstream post(Upstream obj) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(obj, HttpMethod.REQ_POST, PATH + "/"), type);
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