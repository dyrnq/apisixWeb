package com.dyrnq.filter;

import com.dyrnq.CfgExtractor;
import com.dyrnq.model.User;
import com.dyrnq.service.BusinessLogic;
import com.dyrnq.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.core.route.RouterInterceptor;
import org.noear.solon.core.route.RouterInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtInterceptor implements RouterInterceptor {
    static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Inject
    BusinessLogic businessLogic;

    @Inject
    CfgExtractor cfgExtractor;

    @Inject("${server.session.state.jwt.secret:${jwt.secret:}}")
    String jwt_secret;

    @Inject("${server.session.state.jwt.prefix:${jwt.prefix:}}")
    String jwt_prefix;

    private Boolean validateToken(Context ctx, String token, String name) {
        if (StringUtils.isBlank(token)) return false;

        Claims claims = JwtUtils.parseJwt(token, jwt_secret, jwt_prefix);
        if (claims == null) return false;
        String username = claims.getSubject();
        User user = businessLogic.findByName(username);
        if (user == null) return false;
        ctx.attrSet("admin", user);

        Date expiration = claims.getExpiration();
        return (username.equals(user.getName()) && !expiration.before(new Date()));
    }

    @Override
    public void doIntercept(Context ctx, Handler mainHandler, RouterInterceptorChain chain) throws Throwable {
        if ((ctx.path().startsWith("/admin") && !ctx.path().startsWith("/admin/login"))
                || (ctx.path().startsWith("/api"))) {
            String token = ctx.cookie(cfgExtractor.tokenCookieName());

            boolean validateToken = false;
            try {
                validateToken = validateToken(ctx, token, null);
            } finally {
                if (!validateToken) {
                    if (ctx.path().startsWith("/api")) {
                        ctx.status(401);
                    } else {
                        ctx.redirect("/admin/login", 302);
                    }
                }
            }
        }

        chain.doIntercept(ctx, mainHandler);
    }
}
