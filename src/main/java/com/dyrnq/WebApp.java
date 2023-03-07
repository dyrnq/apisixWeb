package com.dyrnq;

import com.cym.utils.VersionUtils;
import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.core.route.RouterInterceptor;
import org.noear.solon.core.route.RouterInterceptorChain;
import org.noear.solon.scheduling.annotation.EnableScheduling;
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

            app.onEvent(freemarker.template.Configuration.class, cfg -> {
                cfg.setSetting("classic_compatible", "true");
                cfg.setSetting("number_format", "0.##");
                cfg.setSetting("default_encoding","UTF-8");
                cfg.setSetting("template_update_delay","0");
                cfg.setSetting("cache_storage","soft:1");

            });

            System.out.println(VersionUtils.getVersionFromPom());

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

        @Override
        public void doIntercept(Context ctx, Handler mainHandler, RouterInterceptorChain chain) throws Throwable {
            //如果是登录页则不处理
            if("/".equals(ctx.path()) == false) {
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
