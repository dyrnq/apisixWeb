package com.dyrnq.controller;

import com.apiseven.apisix.admin.model.response.Wrap;
import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.AuthHandler;
import com.dyrnq.TokenExpiredException;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.dso.InstMapper;
import com.dyrnq.model.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Before;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.i18n.annotation.I18n;
import org.noear.solon.sessionstate.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

@Before(AuthHandler.class)
@Mapping("admin")
@Controller
@I18n
public class AdminController {
    static Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Mapping("inst")
    public Object inst() {
        ModelAndView model = new ModelAndView("admin/inst.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }
    @Mapping("user")
    public Object user() {
        ModelAndView model = new ModelAndView("admin/user.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("about")
    public Object about() {
        ModelAndView model = new ModelAndView("admin/about.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }
    @Mapping("route")
    public Object route() {
        ModelAndView model = new ModelAndView("admin/route.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("plugin")
    public Object plugin() {
        ModelAndView model = new ModelAndView("admin/plugin.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("streamRoute")
    public Object streamRoute() {
        ModelAndView model = new ModelAndView("admin/streamRoute.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("upstream")
    public Object upstream() {
        ModelAndView model = new ModelAndView("admin/upstream.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("service")
    public Object service() {
        ModelAndView model = new ModelAndView("admin/service.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("secret")
    public Object secret() {
        ModelAndView model = new ModelAndView("admin/secret.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("ssl")
    public Object ssl() {
        ModelAndView model = new ModelAndView("admin/ssl.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }


    @Mapping("consumer")
    public Object consumer() {
        ModelAndView model = new ModelAndView("admin/consumer.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("login")
    public Object login() {
        ModelAndView model = new ModelAndView("admin/login.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("globalRule")
    public Object globalRule() {
        ModelAndView model = new ModelAndView("admin/globalRule.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("pluginConfig")
    public Object pluginConfig() {
        ModelAndView model = new ModelAndView("admin/pluginConfig.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    @Mapping("consumerGroup")
    public Object consumerGroup() {
        ModelAndView model = new ModelAndView("admin/consumerGroup.html");
        model.put("title", "dock");
        model.put("message", "你好 world!");
        return model;
    }

    private AdminClient getAdminClient(){
        String url ="192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile (url ,"", c);
        AdminClient client =new AdminClient(p);
        return client;
    }

    @Mapping("editor")
    public Object editor(Context ctx,String cls,String id) {
        ModelAndView model = new ModelAndView("admin/editor.html");
        model.put("title", "dock");
        String jsonObj = "{}";
        Gson gson = new GsonBuilder().setPrettyPrinting().setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f){
                        if ("id".equals(f.getName()) || "createTime".equals(f.getName()) || "updateTime".equals(f.getName()) ) {
                            return true; // 如果是特殊字段，则排除
                        }
                        return false; // 其他字段都保留
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();

        //Type type = new TypeToken<Wrap<Route>>(){}.getType();
        try {
            switch(cls){
                case "route" :
                    jsonObj = gson.toJson(getAdminClient().getRoute(id));
                    break;
                case "upstream" :
                    jsonObj = gson.toJson(getAdminClient().getUpstream(id));
                    break;
                case "ssl" :
                    jsonObj = gson.toJson(getAdminClient().getSSL(id));
                    break;
                case "service" :
                    jsonObj = gson.toJson(getAdminClient().getService(id));
                    break;
                case "streamRoute" :
                    jsonObj = gson.toJson(getAdminClient().getStreamRoute(id));
                    break;
                case "secret" :
                    jsonObj = gson.toJson(getAdminClient().getSecret(id));
                    break;
                case "consumer" :
                    jsonObj = gson.toJson(getAdminClient().getConsumer(id));
                    break;
                case "globalRule":
                    jsonObj = gson.toJson(getAdminClient().getGlobalRule(id));
                    break;
                case "pluginConfig":
                    jsonObj = gson.toJson(getAdminClient().getPluginConfig(id));
                    break;
                case "consumerGroup":
                    jsonObj = gson.toJson(getAdminClient().getConsumerGroup(id));
                    break;
                case "plugin":
                    jsonObj = gson.toJson(getAdminClient().getPlugin(id));
                    break;

                default :
                    jsonObj = gson.toJson(getAdminClient().getRoute(id));
            }



        } catch (ApisixSDKExcetion apisixSDKExcetion) {
            logger.error(apisixSDKExcetion.getErrorCode(),apisixSDKExcetion);
//            apisixSDKExcetion.printStackTrace();
        } catch (java.lang.NullPointerException nullPointerException){
            logger.error(nullPointerException.getMessage(),nullPointerException);
        }
        model.put("json", jsonObj  );
        model.put("id",id);
        model.put("cls",cls);

        return model;
    }

//    private Claims getClaimsFromToken(String token) {
//        return JwtUtils.parseJwt(token);
//    }
//
//    public String getUserIdFromToken(String token) throws TokenExpiredException {
//        String userId = null;
//        try {
//            Claims claims = getClaimsFromToken(token);
//            userId = claims.getId();
//        } catch (ExpiredJwtException e) {
//            throw new TokenExpiredException("令牌过期");
//        }
//        return userId;
//    }
//
//    public String getUsernameFromToken(String token) throws TokenExpiredException {
//        String username = null;
//        try {
//            Claims claims = getClaimsFromToken(token);
//            username = claims.getSubject();
//        } catch (ExpiredJwtException e) {
//            throw new TokenExpiredException("令牌过期");
//        }
//        return username;
//    }


    @Mapping("")
    public Object index(Context ctx) {

        String token = ctx.cookie("TOKEN");
//        System.out.println(token);
//
//        String user_name = ctx.session("user_name", "");
        if (Utils.isEmpty(token)) {
            ModelAndView model = new ModelAndView("admin/index-noauth.html");
            model.put("title", "dock");
            model.put("message", "你好 world!");
            return model;
        } else {
            ModelAndView model = new ModelAndView("admin/index-auth.html");
            model.put("title", "dock");
            model.put("message", "你好 world!");
            return model;
        }

    }
}
