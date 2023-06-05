package com.dyrnq.apisix;


import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.profile.HttpProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.response.Wrap;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminClient extends BaseClient {

    public static final String QUERY_PARAMS_PAGE="page";
    public static final String QUERY_PARAMS_PAGE_SIZE="page_size";
    public AdminClient(Profile profile) {
        super(profile);
    }


    public static String mapToQueryString(Map<String, String> params) {
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return URLEncodedUtils.format(nameValuePairs, StandardCharsets.UTF_8);
    }


    public Multi<Route> queryRoutes(String page,String page_size) throws ApisixSDKException {
        Multi<Route> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/routes",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }
    public Multi<StreamRoute> queryStreamRoutes(String page,String page_size) throws ApisixSDKException {
        Multi<StreamRoute> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/stream_routes",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }
    public Multi<Upstream> queryUpstreams(String page,String page_size) throws ApisixSDKException {
        Multi<Upstream> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/upstreams",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }

    public Multi<Secret> querySecrets(String page,String page_size) throws ApisixSDKException {
        Multi<Secret> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/secrets",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }

    public Multi<Service> queryServices(String page,String page_size) throws ApisixSDKException {
        Multi<Service> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/services",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }

    public Multi<Consumer> queryConsumers(String page,String page_size) throws ApisixSDKException {
        Multi<Consumer> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/consumers",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }

    public Multi<ConsumerGroup> queryConsumerGroups(String page,String page_size) throws ApisixSDKException {
        Multi<ConsumerGroup> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/consumer_groups",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }
    public Multi<GlobalRule> queryGlobalRules(String page,String page_size) throws ApisixSDKException {
        Multi<GlobalRule> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/global_rules",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }
    public Multi<PluginConfig> queryPluginConfigs(String page,String page_size) throws ApisixSDKException {
        Multi<PluginConfig> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/plugin_configs",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }


    public List<Route> listRoutes() throws ApisixSDKException {
        Multi<Route> rsp = null;
        try {
            Type type = new TypeToken<Multi<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/routes"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }


        List<Route> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<Map> listPlugins() throws ApisixSDKException {
        Map<String,Map> rsp = null;
        try {
            Type type = new TypeToken<Map<String,Map>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/plugins","all=true"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<Map> result = new ArrayList<Map>();

        if(rsp !=null) {

            for (String key : rsp.keySet()) {

                Map map = new HashMap();
                map.putAll(rsp.get(key));
                map.put("name" ,key );
                map.put("id" ,key );
                result.add(map);
            }

        }



        return result;
    }


    public List<GlobalRule> listGlobalRules() throws ApisixSDKException {
        Multi<GlobalRule> rsp = null;
        try {
            Type type = new TypeToken<Multi<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/global_rules"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<GlobalRule> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<ConsumerGroup> listConsumerGroups() throws ApisixSDKException {
        Multi<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Multi<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumer_groups"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<ConsumerGroup> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<Secret> listSecrets() throws ApisixSDKException {
        Multi<Secret> rsp = null;
        try {
            Type type = new TypeToken<Multi<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/secrets"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<Secret> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }



    public List<StreamRoute> listStreamRoutes() throws ApisixSDKException {
        Multi<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Multi<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/stream_routes"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<StreamRoute> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<PluginConfig> listPluginConfigs() throws ApisixSDKException {
        Multi<PluginConfig> rsp = null;
        try {
            Type type = new TypeToken<Multi<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/plugin_configs"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<PluginConfig> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public StreamRoute getStreamRoute(String id) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/stream_routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Map getPlugin(String id) throws ApisixSDKException {
        Map rsp = null;
        try {
            Type type = new TypeToken<Map>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/plugins/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }

    public GlobalRule getGlobalRule(String id) throws ApisixSDKException {
        Wrap<GlobalRule> rsp = null;
        try {
            Type type = new TypeToken<Wrap<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/global_rules/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public ConsumerGroup getConsumerGroup(String id) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumer_groups/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public PluginConfig getPluginConfig(String id) throws ApisixSDKException {
        Wrap<PluginConfig> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/plugin_configs/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }
    
    public Route getRoute(String id) throws ApisixSDKException {
        Wrap<Route> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public PluginConfig putPluginConfig(String id, PluginConfig route) throws ApisixSDKException {
        Wrap<PluginConfig> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/plugin_configs/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public StreamRoute putStreamRoute(String id, StreamRoute route) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/stream_routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public GlobalRule putGlobalRule(String id, GlobalRule route) throws ApisixSDKException {
        Wrap<GlobalRule> rsp = null;
        try {
            Type type = new TypeToken<Wrap<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/global_rules/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }


        return rsp.getValue();
    }

    public void delRoute(String id) throws ApisixSDKException {
        Wrap<Route> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/routes/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }
    public void delStreamRoute(String id) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/stream_routes/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public void delSecret(String id) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/secrets/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public void delUpstream(String id) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/upstreams/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public void delService(String id) throws ApisixSDKException {
        Wrap<Service> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/services/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }
    public void delConsumer(String id) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/consumers/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }
    public void delConsumerGroup(String id) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/consumer_groups/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }
    public void delGlobalRule(String id) throws ApisixSDKException {
        Wrap<GlobalRule> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/global_rules/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }
    public void delPluginConfig(String id) throws ApisixSDKException {
        Wrap<PluginConfig> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/plugin_configs/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public void delPluginMetadata(String id) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/plugin_metadata/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public Route patchRouteRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<Route> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PATCH, "/apisix/admin/routes/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }
    public SSL patchSSLRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PATCH, "/apisix/admin/ssls/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Route putRouteRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<Route> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/routes/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }
    public StreamRoute putStreamRouteRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<StreamRoute> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/stream_routes/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Upstream putUpstreamRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/upstreams/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Service putServiceRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<Service> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/services/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }
    public Consumer putConsumerRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/consumers/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }
    public ConsumerGroup putConsumerGroupRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/consumer_groups/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public GlobalRule putGlobalRuleRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<GlobalRule> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/global_rules/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }
    public PluginConfig putPluginConfigRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<PluginConfig> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/plugin_configs/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }
    public SSL putSSLRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/ssls/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret putSecretRaw(String id , String rawData) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/secrets/" + id, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    //update route
    public Route putRoute(String id, Route route) throws ApisixSDKException {
        Wrap<Route> rsp = null;
        String upstreamId = "";
        //fetch the old upstreamID
        try {
            Route exist = getRoute(id);
            upstreamId = exist.getUpstreamId();
        }catch (ApisixSDKException e){
        }

        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }



        return rsp.getValue();
    }

    //create route
    public Route postRoute(Route route) throws ApisixSDKException {
        Wrap<Route> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_POST, "/apisix/admin/routes/"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }




    public List<Service> listServices() throws ApisixSDKException {
        Multi<Service> rsp = null;
        try {
            Type type = new TypeToken<Multi<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/services"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<Service> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }


    public Service getService(String id) throws ApisixSDKException {
        Wrap<Service> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/services/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Map getPluginMetadata(String id) throws ApisixSDKException {
        Wrap<Map> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Map>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/plugin_metadata/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public PluginMetadata putPluginMetadata(String id, Map map) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
        try {
            //service = resolveUpstream(service);
            Type type = new TypeToken<Wrap<PluginMetadata>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(map, HttpProfile.REQ_PUT, "/apisix/admin/plugin_metadata/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }




    public Service putService(String id, Service service) throws ApisixSDKException {
        Wrap<Service> rsp = null;
        try {
            //service = resolveUpstream(service);
            Type type = new TypeToken<Wrap<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(service, HttpProfile.REQ_PUT, "/apisix/admin/services/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public Service postService(Service service) throws ApisixSDKException {
        Wrap<Service> rsp = null;
        try {
            //service = resolveUpstream(service);
            Type type = new TypeToken<Wrap<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(service, HttpProfile.REQ_POST, "/apisix/admin/services/"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public List<Upstream> listUpstreams() throws ApisixSDKException {
        Multi<Upstream> rsp = null;
        try {
           
            Type type = new TypeToken<Multi<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/upstreams"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
            	e.printStackTrace();
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<Upstream> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }


    public Upstream getUpstream(String id) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/upstreams/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }





    public Upstream putUpstream(String id, Upstream upstream) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(upstream, HttpProfile.REQ_PUT, "/apisix/admin/upstreams/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public Upstream postUpstream(Upstream upstream) throws ApisixSDKException {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(upstream, HttpProfile.REQ_POST, "/apisix/admin/upstreams/"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public List<Consumer> listConsumers() throws ApisixSDKException {
        Multi<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Multi<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumers"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<Consumer> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }


    public Consumer getConsumer(String username) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumers/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }





    public Consumer putConsumer(String username, Consumer consumer) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_PUT, "/apisix/admin/consumers/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public ConsumerGroup putConsumerGroup(String username, ConsumerGroup consumer) throws ApisixSDKException {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_PUT, "/apisix/admin/consumer_groups/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public Consumer postConsumer(Consumer consumer) throws ApisixSDKException {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_POST, "/apisix/admin/consumers/"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public List<SSL> listSSLs() throws ApisixSDKException {
        Multi<SSL> rsp = null;
        try {
            Type type = new TypeToken<Multi<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/ssls"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }

        List<SSL> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public Multi<SSL> querySSLs(String page,String page_size) throws ApisixSDKException {
        Multi<SSL> rsp = null;
        try {
            Map<String,String> paramsMap= new HashMap<String, String>();
            paramsMap.put(QUERY_PARAMS_PAGE,page);
            paramsMap.put(QUERY_PARAMS_PAGE_SIZE,page_size);
            Type type = new TypeToken<Multi<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/ssls",mapToQueryString(paramsMap)), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp;
    }


    public SSL getSSL(String id) throws ApisixSDKException {
  //      Wrap<SSL> rsp = null;
        try {
//            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
//            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/ssls/" + id), type);

            List<SSL> list = listSSLs();

            SSL fo=null;
            for(SSL l:list){
                if(StringUtils.equalsIgnoreCase(id,l.getId())){
                    fo =l;
                    break;
                }
            }

            if(fo!=null) return fo;


        } catch (ApisixSDKException | JsonSyntaxException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
//        return rsp.getValue();
        return null;




    }





    public SSL putSSL(String id, SSL ssl) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(ssl, HttpProfile.REQ_PUT, "/apisix/admin/ssls/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public SSL postSSL(SSL ssl) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(ssl, HttpProfile.REQ_POST, "/apisix/admin/ssls/"), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret putSecret(String id,String manager, Secret secret) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(secret, HttpProfile.REQ_PUT, "/apisix/admin/secrets/"+manager+"/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret getSecret(String id) throws ApisixSDKException {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/secrets/" + id), type);
        } catch (ApisixSDKException | JsonSyntaxException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public void delSSL(String id) throws ApisixSDKException {
        Wrap<SSL> rsp = null;
        try {
            //route = resolveUpstream(route);
            String s = this.doRequest(null, HttpProfile.REQ_DELETE, "/apisix/admin/ssls/" + id);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
    }

    public PluginMetadata putPluginMetadataRaw(String plugin_name , String rawData) throws ApisixSDKException {
        Wrap<PluginMetadata> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<PluginMetadata>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null, HttpProfile.REQ_PUT, "/apisix/admin/plugin_metadata/" + plugin_name, rawData), type);
        } catch (JsonSyntaxException | ApisixSDKException e) {
            if(e instanceof ApisixSDKException){
                throw e;
            }else {
                throw new ApisixSDKException(e.getMessage());
            }
        }
        return rsp.getValue();
    }

}
