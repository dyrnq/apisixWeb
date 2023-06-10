package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.PluginMetadata;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class PluginMetadataClient extends BaseClient implements Stub<PluginMetadata> {

    public static final String PATH = "/apisix/admin/plugin_metadata";

    public PluginMetadataClient(Profile profile) {
        super(profile);
    }

    public PluginMetadata get(String id) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginMetadata>>() {
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

    public Multi<PluginMetadata> query(String page, String page_size) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public void del(String id) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
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

    public List<PluginMetadata> list() throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public PluginMetadata put(String id, PluginMetadata obj) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginMetadata>>() {
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

    public PluginMetadata putRaw(String id, String rawData) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginMetadata>>() {
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

    public PluginMetadata patchRaw(String id, String rawData) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public PluginMetadata post(PluginMetadata obj) throws ApisixSDKException {
        throw new ApisixSDKException("not support");
    }

    public PluginMetadata put(String id, Map map) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginMetadata>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(map, HttpMethod.REQ_PUT, PluginMetadataClient.PATH + "/" + id), type);
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