package com.dyrnq;

import com.cym.utils.VersionUtils;
import com.dyrnq.dso.UserMapper;
import com.dyrnq.service.BusinessLogic;
import com.palm.easy.util.Captcha;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.*;
import org.noear.solon.core.route.RouterInterceptor;
import org.noear.solon.core.route.RouterInterceptorChain;
import org.noear.solon.scheduling.annotation.EnableScheduling;
import org.noear.solon.sessionstate.jwt.JwtUtils;
import org.noear.wood.WoodConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@EnableScheduling
public class WebApp {
    static Logger logger = LoggerFactory.getLogger(WebApp.class);

    public static void main(String args[]){
        Solon.start(WebApp.class, args, app -> {
            app.onError(e -> logger.error(e.getMessage(), e));

            app.before(c -> {
                String path = c.path();
                while (path.contains("//")) {
                    path = path.replace("//", "/");
                }
                c.pathNew(path);
            });
            app.add("captcha", MethodType.GET, ctx -> Captcha.captcha(ctx));
            app.onEvent(freemarker.template.Configuration.class, cfg -> {
                cfg.setSetting("classic_compatible", "true");
                cfg.setSetting("number_format", "0.##");
                cfg.setSetting("default_encoding","UTF-8");
                cfg.setSetting("template_update_delay","0");
                cfg.setSetting("cache_storage","soft:1");

            });


            if (Solon.cfg().isDebugMode()) {
                //执行后打印下sql
                WoodConfig.onExecuteAft(cmd -> {
                    System.out.println(cmd.text + "\r\n" + ONode.stringify(cmd.paramMap()));
                });

                WoodConfig.onException((cmd,err)->{
                    System.out.println(cmd.text + "\r\n" + ONode.stringify(cmd.paramMap()));
                });
            }


        });
    }

    @Component
    public static class AppFilter implements Filter {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        @Inject("${solon.app.name}")
        String projectName;
        @Override
        public void doFilter(Context ctx, FilterChain chain) throws Throwable {
            ctx.attrSet("projectName", projectName);
            chain.doFilter(ctx);
        }
    }

    @Component
    public static class JwtInterceptor implements RouterInterceptor {


        private Claims getClaimsFromToken(String token) {
            return JwtUtils.parseJwt(token);
        }

//        public String getUserIdFromToken(String token) throws TokenExpiredException {
//            String userId = null;
//            try {
//                Claims claims = getClaimsFromToken(token);
//                userId = claims.getId();
//            } catch (ExpiredJwtException e) {
//                throw new TokenExpiredException("令牌过期");
//            }
//            return userId;
//        }

        public String getUsernameFromToken(String token) throws TokenExpiredException {
            String username = null;
            try {
                Claims claims = getClaimsFromToken(token);
                username = claims.getSubject();
            } catch (ExpiredJwtException e) {
                logger.error(e.getMessage());
                throw new TokenExpiredException("令牌过期");
            }
            return username;
        }

        public Boolean validateToken(String token, String name) throws Exception {
            if(StringUtils.isBlank(token)) return false;
            String username = getUsernameFromToken(token);
            logger.info("username="+username);
            com.dyrnq.model.User user = businessLogic.findByName(username);
            if (user==null) return false;
            Context.current().attrSet("admin", user);
            Context.current().attrSet("langType","语言切换");
            return (username.equals(user.getName()) && !isTokenExpired(token));
//            return (username.equals(name) && !isTokenExpired(token));
        }
        public Boolean isTokenExpired(String token) throws Exception {
            try {
                Claims claims = getClaimsFromToken(token);
                Date expiration = claims.getExpiration();
                return expiration.before(new Date());
            } catch (Exception e) {
                logger.error(e.getMessage());
                new Throwable(e);
            }
            return true;
        }

        @Inject
        BusinessLogic businessLogic;
        @Override
        public void doIntercept(Context ctx, Handler mainHandler, RouterInterceptorChain chain) throws Throwable {
            //如果是登录页则不处理
            logger.info("ctx.path()="+ctx.path());
            if((ctx.path().startsWith("/admin") && ! ctx.path().startsWith("/admin/login") ) || ( ctx.path().startsWith("/api") )  ) {
                String token = ctx.cookie("TOKEN");
                logger.info("TOKEN="+token);
//                String session_username = ctx.session("user_name", "");
//                logger.info("session_username="+session_username);
                boolean validateToken = validateToken(token,null);
                if(validateToken){
                }else{
                    if(ctx.path().startsWith("/api")) {
                        ctx.status(401);
                    }else {
                        ctx.redirect("/admin/login", 302);
                    }
                    return;
                }
            }

            chain.doIntercept(ctx, mainHandler);
        }
    }
}
