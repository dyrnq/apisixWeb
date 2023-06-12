package com.dyrnq;

import cn.hutool.core.util.StrUtil;
import com.cym.utils.VersionUtils;
import com.dyrnq.service.BusinessLogic;

import io.jsonwebtoken.Claims;

import org.apache.commons.lang3.StringUtils;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.*;
import org.noear.solon.core.route.RouterInterceptor;
import org.noear.solon.core.route.RouterInterceptorChain;
import org.noear.solon.core.util.LogUtil;
import org.noear.solon.i18n.I18nUtil;
import org.noear.solon.logging.utils.LogUtilToSlf4j;
import org.noear.solon.scheduling.annotation.EnableScheduling;
import org.noear.solon.sessionstate.jwt.JwtUtils;
import org.noear.wood.WoodConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@EnableScheduling
public class WebApp {
    static Logger logger = LoggerFactory.getLogger(WebApp.class);

    public static void main(String args[]) {
        Solon.start(WebApp.class, args, app -> {
            LogUtil.globalSet(new LogUtilToSlf4j());
            app.onError(e -> logger.error(e.getMessage(), e));

            app.before(c -> {
                String path = c.path();
                while (path.contains("//")) {
                    path = path.replace("//", "/");
                }
                c.pathNew(path);
            });

            app.onEvent(freemarker.template.Configuration.class, cfg -> {
                cfg.setSetting("classic_compatible", "true");
                cfg.setSetting("number_format", "0.##");
                cfg.setSetting("default_encoding", "UTF-8");
                cfg.setSetting("template_update_delay", "0");
                cfg.setSetting("cache_storage", "soft:1");
                //cfg.setSetting("strict_syntax","false");

            });


            if (Solon.cfg().isDebugMode()) {
                //执行后打印下sql
                WoodConfig.onExecuteAft(cmd -> {
                    System.out.println(cmd.text + "\r\n" + ONode.stringify(cmd.paramMap()));
                });

                WoodConfig.onException((cmd, err) -> {
                    System.out.println(cmd.text + "\r\n" + ONode.stringify(cmd.paramMap()));
                });
            }


        });
    }

    static String getCtxStr(Context context) {
        String httpHost = context.header("X-Forwarded-Host");
        String realPort = context.header("X-Forwarded-Port");
        String host = context.header("Host");

        String ctx = "//";
        if (StrUtil.isNotEmpty(httpHost)) {
            ctx += httpHost;
        } else if (StrUtil.isNotEmpty(host)) {
            ctx += host;
            if (!host.contains(":") && StrUtil.isNotEmpty(realPort)) {
                ctx += ":" + realPort;
            }
        } else {
            host = context.url().split("/")[2];
            ctx += host;
            if (!host.contains(":") && StrUtil.isNotEmpty(realPort)) {
                ctx += ":" + realPort;
            }
        }
        return ctx;
    }

    @Component
    public static class AppFilter implements Filter {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        @Inject("${solon.app.name}")
        String projectName;

//        @Inject("${solon.app.cfg}")
//        Map<String,Object> map;

//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
//                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
//                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
//                .create();


        @Override
        public void doFilter(Context ctx, FilterChain chain) throws Throwable {
            ctx.attrSet("projectName", projectName);
            ctx.attrSet("cfg", "{ \"pageLimit\":10, \"pageLimits\":[10,50,100]}");
            ctx.attrSet("ctx", getCtxStr(ctx));
            ctx.attrSet("jsrandom", VersionUtils.getVersion());
            chain.doFilter(ctx);
        }
    }


    public static class Message {
        String key;
        String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Component
    public static class I18nFilter implements Filter {

//        @Inject
//        MessageUtil m;

        @Override
        public void doFilter(Context ctx, FilterChain chain) throws Throwable {

//        Properties properties = null;
//        String l = ctx.param("l");
//		  if (StrUtil.isNotEmpty(l) && l.equals("en_US") || settingService.get("lang") != null && settingService.get("lang").equals("en_US")) {
//            settingService.set("lang", "en_US");
//            properties = m.getPropertiesEN();
//        } else {
//            settingService.set("lang", "");
//            properties = m.getProperties();
//        }
//            properties=m.getProperties();

            Properties properties = I18nUtil.getMessageBundle().toProps();

            // js国际化
            Set<String> messageHeaders = new HashSet<>();
            List<Message> messages = new ArrayList<>();
            for (String key : properties.stringPropertyNames()) {
                Message message = new WebApp.Message();
                message.setKey(key);
                message.setValue(properties.getProperty(key));
                messages.add(message);

                messageHeaders.add(key.split("\\.")[0]);
            }

            ctx.attrSet("messageHeaders", messageHeaders);
            ctx.attrSet("messages", messages);

            // html国际化
//            for (String key : messageHeaders) {
//                Map<String, String> map = new HashMap<>();
//                for (Message message : messages) {
//                    if (message.getKey().split("\\.")[0].equals(key)) {
//                        map.put(message.getKey().split("\\.")[1], message.getValue());
//                    }
//                }
//                ctx.attrSet(key, map);
//            }
            chain.doFilter(ctx);
        }

    }

    @Component
    public static class JwtInterceptor implements RouterInterceptor {


//        private Claims getClaimsFromToken(String token) {
//            return JwtUtils.parseJwt(token);
//        }

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

//        public String getUsernameFromToken(String token) throws TokenExpiredException {
//            String username = null;
//            try {
//                Claims claims = getClaimsFromToken(token);
//                username = claims.getSubject();
//            } catch (NullPointerException e) {
//                logger.error(e.getMessage());
//                throw new TokenExpiredException("令牌过期");
//            } catch (ExpiredJwtException e) {
//                logger.error(e.getMessage());
//                throw new TokenExpiredException("令牌过期");
//            }
//            return username;
//        }

        private Boolean validateToken(Context ctx ,String token, String name) {
            if (StringUtils.isBlank(token)) return false;

            Claims claims = JwtUtils.parseJwt(token);
            String username = claims.getSubject();
            logger.info("username=" + username);
            com.dyrnq.model.User user = businessLogic.findByName(username);
            if (user == null) return false;
            ctx.attrSet("admin", user);
            ctx.attrSet("langType", "语言切换");

            Date expiration = claims.getExpiration();
//            return expiration.before(new Date());
            return (username.equals(user.getName()) && ! expiration.before(new Date()));
//            return (username.equals(name) && !isTokenExpired(token));
        }

//        public Boolean isTokenExpired(String token) throws Exception {
//            try {
//                Claims claims = getClaimsFromToken(token);
//                Date expiration = claims.getExpiration();
//                return expiration.before(new Date());
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//                new Throwable(e);
//            }
//            return true;
//        }

        @Inject
        BusinessLogic businessLogic;

        @Override
        public void doIntercept(Context ctx, Handler mainHandler, RouterInterceptorChain chain) throws Throwable {
            //如果是登录页则不处理
            logger.info("ctx.path()=" + ctx.path());
            if ((ctx.path().startsWith("/admin") && !ctx.path().startsWith("/admin/login")) || (ctx.path().startsWith("/api"))) {
                String token = ctx.cookie("TOKEN");
                logger.info("TOKEN=" + token);
//                String session_username = ctx.session("user_name", "");
//                logger.info("session_username="+session_username);
                boolean validateToken =false;
                try {
                    validateToken = validateToken(ctx,token, null);
                } finally {
                    if (!validateToken) {
                        if (ctx.path().startsWith("/api")) {
                            ctx.status(401);
                        } else {
                            ctx.redirect("/admin/login", 302);
                        }
                        return;
                    }
                }

            }

            chain.doIntercept(ctx, mainHandler);
        }
    }
}
