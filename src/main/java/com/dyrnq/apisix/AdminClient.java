package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public class AdminClient extends BaseClient {


    public AdminClient(Profile profile) {
        super(profile);
    }


    public Multi<Route> queryRoutes(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        return new RouteClient(getProfile()).query(page, page_size, qp);
    }

    public Multi<StreamRoute> queryStreamRoutes(String page, String page_size) throws ApisixSDKException {
        return new StreamRouteClient(getProfile()).query(page, page_size);
    }

    public Multi<Upstream> queryUpstreams(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        return new UpstreamClient(getProfile()).query(page, page_size, qp);
    }

    public Multi<Secret> querySecrets(String page, String page_size) throws ApisixSDKException {
        return new SecretClient(getProfile()).query(page, page_size);
    }

    public Multi<Service> queryServices(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        return new ServiceClient(getProfile()).query(page, page_size, qp);
    }

    public Multi<Consumer> queryConsumers(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        return new ConsumerClient(getProfile()).query(page, page_size, qp);
    }

    public Multi<ConsumerGroup> queryConsumerGroups(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        return new ConsumerGroupClient(getProfile()).query(page, page_size, qp);
    }

    public Multi<GlobalRule> queryGlobalRules(String page, String page_size) throws ApisixSDKException {
        return new GlobalRuleClient(getProfile()).query(page, page_size);
    }

    public Multi<PluginConfig> queryPluginConfigs(String page, String page_size, Map<String, String> qp) throws ApisixSDKException {
        return new PluginConfigClient(getProfile()).query(page, page_size, qp);
    }


    public List<Route> listRoutes() throws ApisixSDKException {
        return new RouteClient(getProfile()).list();
    }

    public List<Map> listPlugins() throws ApisixSDKException {
        return new PluginClient(getProfile()).list();
    }


    public List<GlobalRule> listGlobalRules() throws ApisixSDKException {
        return new GlobalRuleClient(getProfile()).list();
    }

    public List<ConsumerGroup> listConsumerGroups() throws ApisixSDKException {
        return new ConsumerGroupClient(getProfile()).list();
    }

    public List<Secret> listSecrets() throws ApisixSDKException {
        return new SecretClient(getProfile()).list();
    }


    public List<StreamRoute> listStreamRoutes() throws ApisixSDKException {
        return new StreamRouteClient(getProfile()).list();
    }

    public List<PluginConfig> listPluginConfigs() throws ApisixSDKException {
        return new PluginConfigClient(getProfile()).list();
    }

    public StreamRoute getStreamRoute(String id) throws ApisixSDKException {
        return new StreamRouteClient(getProfile()).get(id);
    }

    public Map getPlugin(String id) throws ApisixSDKException {
        return new PluginClient(getProfile()).get(id);
    }

    public GlobalRule getGlobalRule(String id) throws ApisixSDKException {
        return new GlobalRuleClient(getProfile()).get(id);
    }

    public ConsumerGroup getConsumerGroup(String id) throws ApisixSDKException {
        return new ConsumerGroupClient(getProfile()).get(id);
    }

    public PluginConfig getPluginConfig(String id) throws ApisixSDKException {
        return new PluginConfigClient(getProfile()).get(id);
    }

    public Route getRoute(String id) throws ApisixSDKException {
        return new RouteClient(getProfile()).get(id);
    }


    public PluginConfig putPluginConfig(String id, PluginConfig obj) throws ApisixSDKException {
        return new PluginConfigClient(getProfile()).put(id, obj);
    }


    public StreamRoute putStreamRoute(String id, StreamRoute obj) throws ApisixSDKException {
        return new StreamRouteClient(getProfile()).put(id, obj);
    }


    public GlobalRule putGlobalRule(String id, GlobalRule obj) throws ApisixSDKException {
        return new GlobalRuleClient(getProfile()).put(id, obj);
    }

    public void delRoute(String id) throws ApisixSDKException {
        new RouteClient(getProfile()).del(id);
    }

    public void delStreamRoute(String id) throws ApisixSDKException {
        new StreamRouteClient(getProfile()).del(id);
    }

    public void delSecret(String id) throws ApisixSDKException {
        new SecretClient(getProfile()).del(id);
    }

    public void delProto(String id) throws ApisixSDKException {
        new ProtoClient(getProfile()).del(id);
    }

    public void delUpstream(String id) throws ApisixSDKException {
        new UpstreamClient(getProfile()).del(id);
    }

    public void delService(String id) throws ApisixSDKException {
        new ServiceClient(getProfile()).del(id);
    }

    public void delConsumer(String id) throws ApisixSDKException {
        new ConsumerClient(getProfile()).del(id);
    }

    public void delConsumerGroup(String id) throws ApisixSDKException {
        new ConsumerGroupClient(getProfile()).del(id);
    }

    public void delGlobalRule(String id) throws ApisixSDKException {
        new GlobalRuleClient(getProfile()).del(id);
    }

    public void delPluginConfig(String id) throws ApisixSDKException {
        new PluginConfigClient(getProfile()).del(id);
    }

    public void delPluginMetadata(String id) throws ApisixSDKException {
        new PluginMetadataClient(getProfile()).del(id);
    }

    public Route patchRouteRaw(String id, String rawData) throws ApisixSDKException {
        return new RouteClient(getProfile()).patchRaw(id, rawData);
    }

    public SSL patchSSLRaw(String id, String rawData) throws ApisixSDKException {
        return new SSLClient(getProfile()).patchRaw(id, rawData);
    }

    public Route putRouteRaw(String id, String rawData) throws ApisixSDKException {
        return new RouteClient(getProfile()).putRaw(id, rawData);
    }

    public StreamRoute putStreamRouteRaw(String id, String rawData) throws ApisixSDKException {
        return new StreamRouteClient(getProfile()).putRaw(id, rawData);
    }

    public Upstream putUpstreamRaw(String id, String rawData) throws ApisixSDKException {
        return new UpstreamClient(getProfile()).putRaw(id, rawData);
    }

    public Service putServiceRaw(String id, String rawData) throws ApisixSDKException {
        return new ServiceClient(getProfile()).putRaw(id, rawData);
    }

    public Consumer putConsumerRaw(String id, String rawData) throws ApisixSDKException {
        return new ConsumerClient(getProfile()).putRaw(id, rawData);
    }

    public ConsumerGroup putConsumerGroupRaw(String id, String rawData) throws ApisixSDKException {
        return new ConsumerGroupClient(getProfile()).putRaw(id, rawData);
    }

    public GlobalRule putGlobalRuleRaw(String id, String rawData) throws ApisixSDKException {
        return new GlobalRuleClient(getProfile()).putRaw(id, rawData);
    }

    public PluginConfig putPluginConfigRaw(String id, String rawData) throws ApisixSDKException {
        return new PluginConfigClient(getProfile()).putRaw(id, rawData);
    }

    public SSL putSSLRaw(String id, String rawData) throws ApisixSDKException {
        return new SSLClient(getProfile()).putRaw(id, rawData);
    }

    public Secret putSecretRaw(String id, String rawData) throws ApisixSDKException {
        return new SecretClient(getProfile()).putRaw(id, rawData);
    }

    //update route
    public Route putRoute(String id, Route route) throws ApisixSDKException {
        return new RouteClient(getProfile()).put(id, route);
    }

    //create route
    public Route postRoute(Route route) throws ApisixSDKException {
        return new RouteClient(getProfile()).post(route);
    }


    public List<Service> listServices() throws ApisixSDKException {
        return new ServiceClient(getProfile()).list();
    }


    public Service getService(String id) throws ApisixSDKException {
        return new ServiceClient(getProfile()).get(id);
    }

    public Map getPluginMetadata(String id) throws ApisixSDKException {
        Wrap<Map> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Map>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(HttpMethod.REQ_GET, PluginMetadataClient.PATH + "/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public PluginMetadata putPluginMetadata(String id, Map map) throws ApisixSDKException {
        return new PluginMetadataClient(getProfile()).put(id, map);
    }


    public Service putService(String id, Service obj) throws ApisixSDKException {
        return new ServiceClient(getProfile()).put(id, obj);
    }


    public Service postService(Service obj) throws ApisixSDKException {
        return new ServiceClient(getProfile()).post(obj);
    }


    public List<Upstream> listUpstreams() throws ApisixSDKException {
        return new UpstreamClient(getProfile()).list();
    }


    public Upstream getUpstream(String id) throws ApisixSDKException {
        return new UpstreamClient(getProfile()).get(id);
    }


    public Upstream putUpstream(String id, Upstream obj) throws ApisixSDKException {
        return new UpstreamClient(getProfile()).put(id, obj);
    }


    public Upstream postUpstream(Upstream obj) throws ApisixSDKException {
        return new UpstreamClient(getProfile()).post(obj);
    }


    public List<Consumer> listConsumers() throws ApisixSDKException {
        return new ConsumerClient(getProfile()).list();
    }


    public Consumer getConsumer(String username) throws ApisixSDKException {
        return new ConsumerClient(getProfile()).get(username);
    }


    public Consumer putConsumer(String username, Consumer consumer) throws ApisixSDKException {
        return new ConsumerClient(getProfile()).put(username, consumer);
    }

    public ConsumerGroup putConsumerGroup(String username, ConsumerGroup consumer) throws ApisixSDKException {
        return new ConsumerGroupClient(getProfile()).put(username, consumer);
    }


    public List<SSL> listSSLs() throws ApisixSDKException {
        return new SSLClient(getProfile()).list();
    }

    public Multi<SSL> querySSLs(String page, String page_size, Map<String, String> pq) throws ApisixSDKException {
        return new SSLClient(getProfile()).query(page, page_size, pq);
    }


    public SSL getSSL(String id) throws ApisixSDKException {
        try {
            List<SSL> list = listSSLs();
            SSL fo = null;
            for (SSL l : list) {
                if (StringUtils.equalsIgnoreCase(id, l.getId())) {
                    fo = l;
                    break;
                }
            }
            if (fo != null) return fo;
        } catch (ApisixSDKException | JsonSyntaxException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return null;
    }


    public SSL putSSL(String id, SSL ssl) throws ApisixSDKException {
        return new SSLClient(getProfile()).put(id, ssl);
    }


    public SSL postSSL(SSL ssl) throws ApisixSDKException {
        return new SSLClient(getProfile()).post(ssl);
    }

    public Secret putSecret(String id, String manager, Secret secret) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>() {
            }.getType();
            rsp = gson.fromJson(this.doRequest(secret, HttpMethod.REQ_PUT, "/apisix/admin/secrets/" + manager + "/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if (e instanceof ApisixSDKException) {
                throw e;
            } else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret getSecret(String id) throws ApisixSDKException {
        return new SecretClient(getProfile()).get(id);
    }

    public void delSSL(String id) throws ApisixSDKException {
        new SSLClient(getProfile()).del(id);
    }

    public PluginMetadata putPluginMetadataRaw(String plugin_name, String rawData) throws ApisixSDKException {
        return new PluginMetadataClient(getProfile()).putRaw(plugin_name, rawData);
    }

    public Multi<Proto> queryProtos(String page, String page_size) throws ApisixSDKException {
        return new ProtoClient(getProfile()).query(page, page_size);
    }

    public Proto putProto(String id, Proto proto) throws ApisixSDKException {
        return new ProtoClient(getProfile()).put(id, proto);
    }

    public Proto getProto(String id) throws ApisixSDKException {
        return new ProtoClient(getProfile()).get(id);
    }


    public Proto putProtoRaw(String id, String rawData) throws ApisixSDKException {
        return new ProtoClient(getProfile()).putRaw(id, rawData);
    }


    public List<Proto> listProtos() throws ApisixSDKException {
        return new ProtoClient(getProfile()).list();
    }
}
