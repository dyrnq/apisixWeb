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
import com.dyrnq.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Before;
import org.noear.solon.annotation.Controller;
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
    @Mapping("about")
    public Object about() {
        ModelAndView model = new ModelAndView("admin/about.html");
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

    @Mapping("editor")
    public Object editor(Context ctx,String id) {
        ModelAndView model = new ModelAndView("admin/editor.html");
        model.put("title", "dock");
        String url ="192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile (url ,"", c);

        String jsonObj = "{}";
        AdminClient client =new AdminClient(p);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type type = new TypeToken<Wrap<Route>>(){}.getType();
        try {
            jsonObj = gson.toJson(client.getRoute(id));
        } catch (ApisixSDKExcetion apisixSDKExcetion) {
            logger.error(apisixSDKExcetion.getErrorCode(),apisixSDKExcetion);
//            apisixSDKExcetion.printStackTrace();
        }
        model.put("json", jsonObj  );

        return model;
    }

    private Claims getClaimsFromToken(String token) {
        return JwtUtils.parseJwt(token);
    }

    public String getUserIdFromToken(String token) throws TokenExpiredException {
        String userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getId();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("令牌过期");
        }
        return userId;
    }

    public String getUsernameFromToken(String token) throws TokenExpiredException {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("令牌过期");
        }
        return username;
    }


    @Mapping("")
    public Object index(Context ctx) {

        String token = ctx.cookie("TOKEN");
        System.out.println(token);

        String user_name = ctx.session("user_name", "");
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
