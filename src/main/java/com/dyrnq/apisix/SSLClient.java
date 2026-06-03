package com.dyrnq.apisix;

import com.dyrnq.apisix.domain.SSL;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class SSLClient extends BaseClient implements Stub<SSL> {

    public static final String PATH = "/apisix/admin/ssls";

    public SSLClient(Profile profile) {
        super(profile);
    }

    public SSL get(String id) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>() {}.getType();
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

    public Multi<SSL> query(String page, String page_size) throws ApisixSDKException {
        return query(page, page_size, null);
    }

    public Multi<SSL> query(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        Multi<SSL> rsp = null;
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE, page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE, page_size);
            if (qp != null) {
                for (Map.Entry<String, String> entry : qp.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        paramsMap.put(key, value);
                    }
                }
            }
            Type type = new TypeToken<Multi<SSL>>() {}.getType();
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
        Wrap<SSL> rsp = null;
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

    public List<SSL> list() throws ApisixSDKException {
        Multi<SSL> rsp = null;
        try {
            Type type = new TypeToken<Multi<SSL>>() {}.getType();
            rsp = gson.fromJson(this.doRequest(HttpMethod.REQ_GET, PATH), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        List<SSL> result = this.arrangeMulti(rsp.getNodes());
        return result;
    }

    public SSL put(String id, SSL obj) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>() {}.getType();
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

    public SSL putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>() {}.getType();
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

    public SSL patchRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>() {}.getType();
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

    public SSL post(SSL obj) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>() {}.getType();
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
