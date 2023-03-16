package com.dyrnq.apisix;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apiseven.apisix.common.utils.Md5Util;
import com.dyrnq.apisix.domain.*;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

//import com.apiseven.apisix.admin.model.Route;
//import com.apiseven.apisix.admin.model.Upstream;
//import com.apiseven.apisix.admin.model.Service;
//import com.apiseven.apisix.admin.model.Consumer;
//import com.apiseven.apisix.admin.model.SSL;
//import com.apiseven.apisix.admin.model.K8sDeploymentInfo;

import com.apiseven.apisix.admin.model.response.Item;
import com.apiseven.apisix.admin.model.response.Multi;
import com.apiseven.apisix.admin.model.response.Wrap;
import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.HttpProfile;
import com.apiseven.apisix.common.profile.Profile;


public class AdminClient extends BaseClient {

    public AdminClient(Profile profile) {
        super(profile);
    }

    public List<Route> listRoutes() throws ApisixSDKExcetion {
        Multi<Route> rsp = null;
        try {
            Type type = new TypeToken<Multi<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/routes"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }


        List<Route> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<Map> listPlugins() throws ApisixSDKExcetion {
        Map<String,Map> rsp = null;
        try {
            Type type = new TypeToken<Map<String,Map>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(null,HttpProfile.REQ_GET, "/apisix/admin/plugins","all=true"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
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


    public List<GlobalRule> listGlobalRules() throws  ApisixSDKExcetion {
        Multi<GlobalRule> rsp = null;
        try {
            Type type = new TypeToken<Multi<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/global_rules"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<GlobalRule> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<ConsumerGroup> listConsumerGroups() throws  ApisixSDKExcetion {
        Multi<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Multi<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumer_groups"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<ConsumerGroup> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<Secret> listSecrets() throws ApisixSDKExcetion {
        Multi<Secret> rsp = null;
        try {
            Type type = new TypeToken<Multi<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/secrets"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Secret> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }



    public List<StreamRoute> listStreamRoutes() throws ApisixSDKExcetion {
        Multi<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Multi<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/stream_routes"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<StreamRoute> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public List<PluginConfig> listPluginConfigs() throws ApisixSDKExcetion {
        Multi<PluginConfig> rsp = null;
        try {
            Type type = new TypeToken<Multi<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/plugin_configs"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<PluginConfig> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }

    public StreamRoute getStreamRoute(String id) throws ApisixSDKExcetion {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/stream_routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Map getPlugin(String id) throws ApisixSDKExcetion {
        Map rsp = null;
        try {
            Type type = new TypeToken<Map>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/plugins/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp;
    }

    public GlobalRule getGlobalRule(String id) throws ApisixSDKExcetion {
        Wrap<GlobalRule> rsp = null;
        try {
            Type type = new TypeToken<Wrap<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/global_rules/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public ConsumerGroup getConsumerGroup(String id) throws ApisixSDKExcetion {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumer_groups/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public PluginConfig getPluginConfig(String id) throws ApisixSDKExcetion {
        Wrap<PluginConfig> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/plugin_configs/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Route getRoute(String id) throws ApisixSDKExcetion {
        Wrap<Route> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public boolean deleteRoute(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/routes/" + id);
        return true;
    }

    public boolean deleteStreamRoute(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/stream_routes/" + id);
        return true;
    }
    public PluginConfig putPluginConfig(String id, PluginConfig route) throws ApisixSDKExcetion {
        Wrap<PluginConfig> rsp = null;
        try {
            Type type = new TypeToken<Wrap<PluginConfig>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/plugin_configs/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public StreamRoute putStreamRoute(String id, StreamRoute route) throws ApisixSDKExcetion {
        Wrap<StreamRoute> rsp = null;
        try {
            Type type = new TypeToken<Wrap<StreamRoute>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/stream_routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public GlobalRule putGlobalRule(String id, GlobalRule route) throws ApisixSDKExcetion {
        Wrap<GlobalRule> rsp = null;
        try {
            Type type = new TypeToken<Wrap<GlobalRule>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/global_rules/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }


        return rsp.getValue();
    }


    //update route
    public Route putRoute(String id, Route route) throws ApisixSDKExcetion {
        Wrap<Route> rsp = null;
        String upstreamId = "";
        //fetch the old upstreamID
        try {
            Route exist = getRoute(id);
            upstreamId = exist.getUpstreamId();
        }catch (ApisixSDKExcetion e){
        }

        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_PUT, "/apisix/admin/routes/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        // after updated route, if upstreamId changed, try to delete old upstream
//        if(!"".equals(upstreamId) && !upstreamId.equals(route.getUpstreamId())){
//            try {
//                Thread.sleep(500);
//                deleteUpstream(upstreamId);
//            }catch (ApisixSDKExcetion | InterruptedException e){
//            }
//        }

        return rsp.getValue();
    }

    //create route
    public Route postRoute(Route route) throws ApisixSDKExcetion {
        Wrap<Route> rsp = null;
        try {
            //route = resolveUpstream(route);
            Type type = new TypeToken<Wrap<Route>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(route, HttpProfile.REQ_POST, "/apisix/admin/routes/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

//    private Route resolveUpstream(Route route) throws ApisixSDKExcetion {
//        Upstream upstream = route.getUpstream();
//        if(upstream != null) {
//            K8sDeploymentInfo k8sDeploymentInfo = upstream.getK8sDeploymentInfo();
//            //k8s deployment info is not empty
//            if (k8sDeploymentInfo != null) {
//                String namespace = k8sDeploymentInfo.getNamespace();
//                String deployName = k8sDeploymentInfo.getDeployName();
//                String serviceName = k8sDeploymentInfo.getServiceName();
//                if(deployName != null && !deployName.equals("") || (serviceName != null && !serviceName.equals(""))){
//                    String upstreamId = Md5Util.md5(namespace + "-" + deployName + "-" + serviceName + "-" + k8sDeploymentInfo.getPort());
//                    Upstream res = putUpstream(upstreamId, upstream);
//                    if (k8sDeploymentInfo.toString().equals(res.getK8sDeploymentInfo().toString())) {
//                        //replace to upstream id
//                        route.setUpstream(null);
//                        route.setUpstreamId(upstreamId);
//                    }
//                }
//            }
//        }
//        return route;
//    }

//    private Service resolveUpstream(Service service) throws ApisixSDKExcetion {
//        Upstream upstream = service.getUpstream();
//        if(upstream != null) {
//            K8sDeploymentInfo k8sDeploymentInfo = upstream.getK8sDeploymentInfo();
//            //k8s deployment info is not empty
//            if (k8sDeploymentInfo != null) {
//                String namespace = k8sDeploymentInfo.getNamespace();
//                String deployName = k8sDeploymentInfo.getDeployName();
//                String serviceName = k8sDeploymentInfo.getServiceName();
//                if(deployName != null && !deployName.equals("") || (serviceName != null && !serviceName.equals(""))){
//                    String upstreamId = Md5Util.md5(namespace + "-" + deployName + "-" + serviceName + "-" + k8sDeploymentInfo.getPort());
//                    Upstream res = putUpstream(upstreamId, upstream);
//                    if (k8sDeploymentInfo.toString().equals(res.getK8sDeploymentInfo().toString())) {
//                        //replace to upstream id
//                        service.setUpstream(null);
//                        service.setUpstreamId(upstreamId);
//                    }
//                }
//            }
//        }
//        return service;
//    }


    public List<Service> listServices() throws ApisixSDKExcetion {
        Multi<Service> rsp = null;
        try {
            Type type = new TypeToken<Multi<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/services"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Service> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }


    public Service getService(String id) throws ApisixSDKExcetion {
        Wrap<Service> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/services/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public boolean deleteService(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/services/" + id);
        return true;
    }


    public Service putService(String id, Service service) throws ApisixSDKExcetion {
        Wrap<Service> rsp = null;
        try {
            //service = resolveUpstream(service);
            Type type = new TypeToken<Wrap<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(service, HttpProfile.REQ_PUT, "/apisix/admin/services/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public Service postService(Service service) throws ApisixSDKExcetion {
        Wrap<Service> rsp = null;
        try {
            //service = resolveUpstream(service);
            Type type = new TypeToken<Wrap<Service>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(service, HttpProfile.REQ_POST, "/apisix/admin/services/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public List<Upstream> listUpstreams() throws ApisixSDKExcetion {
        Multi<Upstream> rsp = null;
        try {
           
            Type type = new TypeToken<Multi<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/upstreams"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
            	e.printStackTrace();
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Upstream> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }


    public Upstream getUpstream(String id) throws ApisixSDKExcetion {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/upstreams/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public boolean deleteUpstream(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/upstreams/" + id);
        return true;
    }


    public Upstream putUpstream(String id, Upstream upstream) throws ApisixSDKExcetion {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(upstream, HttpProfile.REQ_PUT, "/apisix/admin/upstreams/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public Upstream postUpstream(Upstream upstream) throws ApisixSDKExcetion {
        Wrap<Upstream> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Upstream>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(upstream, HttpProfile.REQ_POST, "/apisix/admin/upstreams/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public List<Consumer> listConsumers() throws ApisixSDKExcetion {
        Multi<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Multi<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumers"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<Consumer> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }


    public Consumer getConsumer(String username) throws ApisixSDKExcetion {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/consumers/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public boolean deleteConsumer(String username) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/consumers/" + username);
        return true;
    }


    public Consumer putConsumer(String username, Consumer consumer) throws ApisixSDKExcetion {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_PUT, "/apisix/admin/consumers/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public ConsumerGroup putConsumerGroup(String username, ConsumerGroup consumer) throws ApisixSDKExcetion {
        Wrap<ConsumerGroup> rsp = null;
        try {
            Type type = new TypeToken<Wrap<ConsumerGroup>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_PUT, "/apisix/admin/consumer_groups/" + username), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public Consumer postConsumer(Consumer consumer) throws ApisixSDKExcetion {
        Wrap<Consumer> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Consumer>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(consumer, HttpProfile.REQ_POST, "/apisix/admin/consumers/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public List<SSL> listSSLs() throws ApisixSDKExcetion {
        Multi<SSL> rsp = null;
        try {
            Type type = new TypeToken<Multi<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/ssls"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }

        List<SSL> result = this.arrangeMulti(rsp.getNodes());

        return result;
    }


    public SSL getSSL(String id) throws ApisixSDKExcetion {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/ssls/" + id), type);
        } catch (ApisixSDKExcetion | JsonSyntaxException e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public boolean deleteSSL(String id) throws ApisixSDKExcetion {
        this.doRequest(HttpProfile.REQ_DELETE, "/apisix/admin/ssls/" + id);
        return true;
    }


    public SSL putSSL(String id, SSL ssl) throws ApisixSDKExcetion {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(ssl, HttpProfile.REQ_PUT, "/apisix/admin/ssls/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }


    public SSL postSSL(SSL ssl) throws ApisixSDKExcetion {
        Wrap<SSL> rsp = null;
        try {
            Type type = new TypeToken<Wrap<SSL>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(ssl, HttpProfile.REQ_POST, "/apisix/admin/ssls/"), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret putSecret(String id,String manager, Secret secret) throws ApisixSDKExcetion {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(secret, HttpProfile.REQ_PUT, "/apisix/admin/secrets/"+manager+"/" + id), type);
        } catch (JsonSyntaxException | ApisixSDKExcetion e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

    public Secret getSecret(String id) throws ApisixSDKExcetion {
        Wrap<Secret> rsp = null;
        try {
            Type type = new TypeToken<Wrap<Secret>>(){}.getType();
            rsp  = gson.fromJson(this.doRequest(HttpProfile.REQ_GET, "/apisix/admin/secrets/" + id), type);
        } catch (ApisixSDKExcetion | JsonSyntaxException e) {
            if(e instanceof ApisixSDKExcetion){
                throw e;
            }else {
                throw new ApisixSDKExcetion(e.getMessage());
            }
        }
        return rsp.getValue();
    }

}
