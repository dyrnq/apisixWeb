package com.dyrnq.controller;

import cn.hutool.core.util.PageUtil;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.DefaultCredential;
import com.dyrnq.apisix.profile.DefaultProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.dso.InstMapper;
import com.dyrnq.dso.UserMapper;
import com.dyrnq.model.Inst;
import com.dyrnq.model.User;
import com.google.gson.*;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapping("api")
@Controller
public class ApiController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Inject
    InstMapper instMapper;
    @Inject
    UserMapper userMapper;
    @Mapping("inst")
    public PageResult inst(Context ctx,int page,int limit) {
        try {
            int start = PageUtil.getStart(page-1,limit);
            IPage<Inst> p = instMapper.selectPage(start,limit,null);
            return PageResult.succeed(p.getList(),p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add/inst")
    public Result addInst(Context ctx,Inst inst){
        try {
            instMapper.insert(inst,true);
            return Result.succeed("ok");
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/inst")
    public Result delInst(Context ctx, String... id ){
        try {
            for(int  i = 0; i < id.length; i++){
                instMapper.deleteById(id[i]);
            }
            return Result.succeed("ok");
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("get/inst")
    public Result getInst(Context ctx,String id){
        try {
            Inst inst = instMapper.selectById(id);
            return Result.succeed(inst);
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("update/inst")
    public Result updateInst(Context ctx,Inst inst){
        try {
            instMapper.updateById(inst,true);
            return Result.succeed("ok");
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }


    @Mapping("user")
    public PageResult user(Context ctx,int page,int limit) {
        try {
            int start = PageUtil.getStart(page-1,limit);
            IPage<User> p = userMapper.selectPage(start,limit,null);
            return PageResult.succeed(p.getList(),p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add/user")
    public Result addUser(Context ctx,User user){
        try {
            userMapper.insert(user,true);
            return Result.succeed("ok");
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/user")
    public Result delUser(Context ctx, String... id ){
        try {
            for(int  i = 0; i < id.length; i++){
                userMapper.deleteById(id[i]);
            }
            return Result.succeed("ok");
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("get/user")
    public Result getUser(Context ctx,String id){
        try {
            User user = userMapper.selectById(id);
            return Result.succeed(user);
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("update/user")
    public Result updateUser(Context ctx,User user){
        try {
            userMapper.updateById(user,true);
            return Result.succeed("ok");
        }catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }



    @Mapping("plugin")
    public Result plugin(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listPlugins());
        } catch (ApisixSDKException e) {
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
        }catch (ApisixSDKException e) {
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
        }catch (ApisixSDKException e) {
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
        }catch (ApisixSDKException e) {
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
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("del/consumer")
    public Result delConsumer(Context ctx,String... id){
        try {
            for(int  i = 0; i < id.length; i++){
                String consumerId = id[i];
                getAdminClient().delConsumer(consumerId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("del/consumerGroup")
    public Result delConsumerGroup(Context ctx,String... id){
        try {
            for(int  i = 0; i < id.length; i++){
                String consumerGroupId = id[i];
                getAdminClient().delConsumerGroup(consumerGroupId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("del/globalRule")
    public Result delGlobalRule(Context ctx,String... id){
        try {
            for(int  i = 0; i < id.length; i++){
                String GlobalRuleId = id[i];
                getAdminClient().delGlobalRule(GlobalRuleId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("del/pluginConfig")
    public Result delPluginConfig(Context ctx,String... id){
        try {
            for(int  i = 0; i < id.length; i++){
                String PluginConfigId = id[i];
                getAdminClient().delPluginConfig(PluginConfigId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/ssl")
    public Result delSSL(Context ctx, String... id ){
        try {
            for(int  i = 0; i < id.length; i++){
                String SSLId = id[i];
                getAdminClient().delSSL(SSLId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del/secret")
    public Result delSecret(Context ctx, String... id ){
        try {
            for(int  i = 0; i < id.length; i++){
                String SecretId = id[i];
                getAdminClient().delSecret(SecretId);
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/ssl")
    public Result addSSLRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putSSLRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("enable/ssl")
    public Result patchSSLRawOn(Context ctx,String... id){
        try {
            for(String i : id) {
                getAdminClient().patchSSLRaw(i, "{\"status\":1}");
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("disable/ssl")
    public Result patchSSLRawOff(Context ctx,String... id){
        try {
            for(String i : id) {
                getAdminClient().patchSSLRaw(i, "{\"status\":0}");
            }
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/route")
    public Result addRouteRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putRouteRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("add/pluginMetadata")
    public Result addPluginMetadataRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putPluginMetadataRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
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
        }catch (ApisixSDKException e) {
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
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/streamRoute")
    public Result addStreamRouteRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putStreamRouteRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/upstream")
    public Result addUpstreamRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putUpstreamRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("add/service")
    public Result addServiceRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putServiceRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/consumer")
    public Result addConsumerRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putConsumerRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/consumerGroup")
    public Result addConsumerGroupRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putConsumerGroupRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/globalRule")
    public Result addGlobalRuleRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putGlobalRuleRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("add/pluginConfig")
    public Result addPluginConfigRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putPluginConfigRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("add/secret")
    public Result addSecretRaw(Context ctx,String id , String rawData){
        try {
            getAdminClient().putSecretRaw(id,rawData);
            return Result.succeed("ok");
        }catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("route")
    public PageResult route(Context ctx,String page,String limit) {
        try {
            Multi<Route> rsp= getAdminClient().queryRoutes(page,limit);
            List<Route> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }


    @Mapping("update/route")
    public Result updateRoute(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listRoutes());
        } catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("streamRoute")
    public PageResult streamRoute(Context ctx,String page,String limit) {
        try {
            Multi<StreamRoute> rsp= getAdminClient().queryStreamRoutes(page,limit);
            List<StreamRoute> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
    @Mapping("consumerGroup")
    public PageResult consumerGroup(Context ctx,String page,String limit) {
        try {
            Multi<ConsumerGroup> rsp= getAdminClient().queryConsumerGroups(page,limit);
            List<ConsumerGroup> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
    @Mapping("upstream")
     public PageResult upstream(Context ctx,String page,String limit) {
        try {
            Multi<Upstream> rsp= getAdminClient().queryUpstreams(page,limit);
            List<Upstream> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("secret")
    public PageResult secret(Context ctx,String page,String limit) {
        try {
            Multi<Secret> rsp= getAdminClient().querySecrets(page,limit);
            List<Secret> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("service")
     public PageResult service(Context ctx,String page,String limit) {
        try {
            Multi<Service> rsp= getAdminClient().queryServices(page,limit);
            List<Service> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
    @Mapping("ssl")
    public Result ssl(Context ctx,String page,String limit) {
        try {
            Multi<SSL> rsp= getAdminClient().querySSLs(page,limit);
            List<SSL> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("globalRule")
    public Result globalRule(Context ctx,String page,String limit) {
        try {
            Multi<GlobalRule> rsp= getAdminClient().queryGlobalRules(page,limit);
            List<GlobalRule> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
    @Mapping("consumer")
    public PageResult consumer(Context ctx,String page,String limit) {
        try {
            Multi<Consumer> rsp= getAdminClient().queryConsumers(page,limit);
            List<Consumer> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("pluginConfig")
    public PageResult pluginConfig(Context ctx,String page,String limit) {
        try {
            Multi<PluginConfig> rsp= getAdminClient().queryPluginConfigs(page,limit);
            List<PluginConfig> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result,rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }


    private AdminClient getAdminClient(){
        String url ="192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile (url ,"", c);
        AdminClient client =new AdminClient(p);
        return client;
    }

    @Mapping("raw")
    public Result getObj(Context ctx, String cls, String id) {

        String jsonStr = "{}";
        Gson gson = new GsonBuilder()
                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        if ("id".equals(f.getName()) || "createTime".equals(f.getName()) || "updateTime".equals(f.getName())) {
                            return true; // 如果是特殊字段，则排除
                        }
                        return false; // 其他字段都保留
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        //Type type = new TypeToken<Wrap<Route>>(){}.getType();
        try {
            switch (cls) {
                case "route":
                    jsonStr = gson.toJson(getAdminClient().getRoute(id));
                    break;
                case "upstream":
                    jsonStr = gson.toJson(getAdminClient().getUpstream(id));
                    break;
                case "ssl":
                    jsonStr = gson.toJson(getAdminClient().getSSL(id));
                    break;
                case "service":
                    jsonStr = gson.toJson(getAdminClient().getService(id));
                    break;
                case "streamRoute":
                    jsonStr = gson.toJson(getAdminClient().getStreamRoute(id));
                    break;
                case "secret":
                    jsonStr = gson.toJson(getAdminClient().getSecret(id));
                    break;
                case "consumer":
                    jsonStr = gson.toJson(getAdminClient().getConsumer(id));
                    break;
                case "globalRule":
                    jsonStr = gson.toJson(getAdminClient().getGlobalRule(id));
                    break;
                case "pluginConfig":
                    jsonStr = gson.toJson(getAdminClient().getPluginConfig(id));
                    break;
                case "pluginMetadata":
                    jsonStr = gson.toJson(getAdminClient().getPluginMetadata(id));
                    break;
                case "consumerGroup":
                    jsonStr = gson.toJson(getAdminClient().getConsumerGroup(id));
                    break;
                case "plugin":
                    jsonStr = gson.toJson(getAdminClient().getPlugin(id));
                    break;
                default:
                    jsonStr = gson.toJson(getAdminClient().getRoute(id));
            }
        } catch (ApisixSDKException apisixSDKException) {
        } catch (NullPointerException nullPointerException) {
        }
        Map map = new HashMap();
        map.put("id", id);
        map.put("rawData", jsonStr);
        return Result.succeed(map);
    }

}
