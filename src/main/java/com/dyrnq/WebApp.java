package com.dyrnq;

import com.cym.utils.VersionUtils;
import com.palm.easy.util.Captcha;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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


        @Override
        public void doIntercept(Context ctx, Handler mainHandler, RouterInterceptorChain chain) throws Throwable {
            //如果是登录页则不处理
            if(
                ctx.path().startsWith("/admin/") &&
                "/admin/".equals(ctx.path()) == false &&
                "/admin/login".equals(ctx.path()) == false

            ) {
                String token = ctx.header("TOKEN");
                System.out.println(token);
                String user_name = ctx.session("user_name", "");
                if (Utils.isEmpty(user_name)) {
                    //说明未登录，则终止处理
                    ctx.status(401);
                    return;
                }
            }

            chain.doIntercept(ctx, mainHandler);
        }
    }
}
