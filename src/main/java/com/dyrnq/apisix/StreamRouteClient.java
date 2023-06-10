package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.StreamRoute;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamRouteClient extends BaseClient implements Stub<StreamRoute> {

    public static final String PATH = "/apisix/admin/stream_routes";

    public StreamRouteClient(Profile profile) {
        super(profile);
    }

    public StreamRoute get(String id) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>() {
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

    public Multi<StreamRoute> query(String page, String page_size) throws ApisixSDKException {
        Multi<StreamRoute> rsp = null;
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE, page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE, page_size);
            Type type = new TypeToken<Multi<StreamRoute>>() {
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
        Wrap<StreamRoute> rsp = null;
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

    public List<StreamRoute> list() throws ApisixSDKException {
        Multi<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Multi<StreamRoute>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpMethod.REQ_GET, PATH), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        List<StreamRoute> result = this.arrangeMulti(rsp.getNodes());
        return result;
    }

    public StreamRoute put(String id, StreamRoute obj) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>() {
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

    public StreamRoute putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>() {
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

    public StreamRoute patchRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>() {
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

    public StreamRoute post(StreamRoute obj) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>() {
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