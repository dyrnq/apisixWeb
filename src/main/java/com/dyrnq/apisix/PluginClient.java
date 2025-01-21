package com.dyrnq.apisix;


import cn.hutool.core.util.StrUtil;
import com.dyrnq.apisix.domain.Plugin;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginClient extends BaseClient implements Stub<Map> {

    public static final String PATH = "/apisix/admin/plugins";

    public PluginClient(Profile profile) {
        super(profile);
    }

    public Map get(String id) throws ApisixSDKException {
        Wrap<Map> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Map>>() {
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

    public Multi<Map> query(String page, String page_size) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public Multi<Map> query(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public void del(String id) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }
    public List<Map> list() throws ApisixSDKException {
        return list(null);
    }

    public List<Map> list(String subsystem) throws ApisixSDKException {
        Map<String, Map> rsp = null;
        try {
            Type type = new TypeToken<Map<String, Map>>() {
            }.getType();
            String param="all=true";
            if(StrUtil.isNotBlank(subsystem)){
                param=param+"&subsystem="+subsystem;
            }
            rsp = gson.fromJson(this.doRequest(null, HttpMethod.REQ_GET, PluginClient.PATH, param), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<Map> result = new ArrayList<Map>();

        if (rsp != null) {

            for (String key : rsp.keySet()) {

                Map map = new HashMap();
                map.putAll(rsp.get(key));
                map.put("name", key);
                map.put("id", key);
                result.add(map);
            }

        }
        return result;
    }

    public Map put(String id, Map obj) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public Map putRaw(String id, String rawData) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public Map patchRaw(String id, String rawData) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public Map post(Plugin obj) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

}