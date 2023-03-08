package com.dyrnq.controller;

import com.dyrnq.TokenExpiredException;
import com.dyrnq.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.core.handle.Result;
import org.noear.solon.i18n.annotation.I18n;
import org.noear.solon.sessionstate.jwt.JwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author noear 2021/8/6 created
 */
@Controller
@I18n
@Mapping("xxx")
public class LoginController {
    /**
     * 登录
     * */
//    @Mapping("/login")
//    public void login(Context ctx, String name) {
//        if ("noear".equals(name)) {
//            //登录成功
//            ctx.sessionSet("user_name", name);
//        }
//    }
    @Mapping("login")
    public Object index() {
        ModelAndView model = new ModelAndView("login.html");
        model.put("title","dock");
        model.put("message","你好 world!");
//        User user = userMapper.selectById("1");
//        System.out.println(user.getName());
        return model;
    }

    /**
     * 退出
     * */
    @Mapping("/logout")
    public void logout(Context ctx) {
        ctx.sessionClear();
    }



    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
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

    /**
     * 登录2。如果想手动传，做为接口的一部分；把：server.session.jwt.allowAutoIssue 设为 false
     */
    @Mapping("/login2")
    public Result login2(Context ctx, String name) {
        if ("noear".equals(name)) {
            Map<String, Object> claims = new HashMap<>(2);
//            claims.put(Claims.SUBJECT, user.getUsername());
//            claims.put(Claims.ISSUED_AT, new Date());
//            claims.put(Claims.ID, user.getId());
            ctx.sessionSet("user_name", name);

            return Result.succeed(ctx.sessionState().sessionToken());
        } else {
            return Result.failure();
        }
    }
}