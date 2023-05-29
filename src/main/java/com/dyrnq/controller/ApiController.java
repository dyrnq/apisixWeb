package com.dyrnq.controller;

import com.apiseven.apisix.admin.model.response.Multi;
import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.*;
import io.jsonwebtoken.Claims;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.List;

@Mapping("api")
@Controller
public class ApiController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(ApiController.class);


    @Mapping("plugin")
    public Result plugin(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listPlugins());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/route")
    public Result delRoute(Context ctx, String... id ){
        try {
            for(int  i = 0; i < id.length; i++){
                String routeId = id[i];
                getAdminClient().delRoute(routeId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/streamRoute")
    public Result delStreamRoute(Context ctx,String... id){
        try {
            for(int  i = 0; i < id.length; i++){
                String StreamRouteId = id[i];
                getAdminClient().delStreamRoute(StreamRouteId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/upstream")
    public Result delUpstream(Context ctx,String... id){
        try {
            for(int  i = 0; i < id.length; i++){
                String UpstreamId = id[i];
                getAdminClient().delUpstream(UpstreamId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/service")
    public Result delService(Context ctx,String... id){
        try {
            for(int  i = 0; i < id.length; i++){
                String serviceId = id[i];
                getAdminClient().delService(serviceId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("add/route")
    public Result addRouteRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putRouteRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("enable/route")
    public Result patchRouteRawOn(Context ctx,String... id){
        try {
            for(String i : id) {
                getAdminClient().patchRouteRaw(i, "{\"status\":1}");
            }
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("disable/route")
    public Result patchRouteRawOff(Context ctx,String... id){
        try {
            for(String i : id) {
                getAdminClient().patchRouteRaw(i, "{\"status\":0}");
            }
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/streamRoute")
    public Result addStreamRouteRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putStreamRouteRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/upstream")
    public Result addUpstreamRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putUpstreamRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("add/service")
    public Result addServiceRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putServiceRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("route")
    public PageResult route(Context ctx,String page,String limit) {
        try {
            Multi<Route> rsp= getAdminClient().queryRoutes(page,limit);
            List<Route> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKExcetion e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }


    @Mapping("update/route")
    public Result updateRoute(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listRoutes());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("streamRoute")
    public PageResult streamRoute(Context ctx,String page,String limit) {
        try {
            Multi<StreamRoute> rsp= getAdminClient().queryStreamRoutes(page,limit);
            List<StreamRoute> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKExcetion e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
    @Mapping("consumerGroup")
    public Result consumerGroup(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listConsumerGroups());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("upstream")
     public PageResult upstream(Context ctx,String page,String limit) {
        try {
            Multi<Upstream> rsp= getAdminClient().queryUpstreams(page,limit);
            List<Upstream> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKExcetion e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("secret")
    public Result secret(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listSecrets());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("service")
     public PageResult service(Context ctx,String page,String limit) {
        try {
            Multi<Service> rsp= getAdminClient().queryServices(page,limit);
            List<Service> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKExcetion e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
    @Mapping("ssl")
    public Result ssl(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listSSLs());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("globalRule")
    public Result globalRule(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listGlobalRules());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("consumer")
    public Result consumer(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listConsumers());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("pluginConfig")
    public Result pluginConfig(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listPluginConfigs());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }


    private AdminClient getAdminClient(){
        String url ="192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile (url ,"", c);
        AdminClient client =new AdminClient(p);
        return client;
    }


}
