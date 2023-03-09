package com.dyrnq.controller;

import com.dyrnq.AuthHandler;
import com.dyrnq.TokenExpiredException;
import com.dyrnq.model.User;
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

@Before(AuthHandler.class)
@Mapping("admin")
@Controller
@I18n
public class AdminController {

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
