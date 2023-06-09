package com.dyrnq.controller;

import com.dyrnq.model.User;
import com.dyrnq.service.BusinessLogic;
import io.jsonwebtoken.Claims;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping("token")
@Controller
public class TokenController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Inject
    BusinessLogic businessLogic;

    /**
     * 获取Token
     *
     * @param name 用户名
     * @param pass 密码
     */
    @Mapping("getToken")
    public Result getToken(Context ctx, String name, String pass) {

        User user = businessLogic.login(name, pass);
        if (user != null) {
            ctx.sessionSet(Claims.SUBJECT, user.getName());
            return Result.succeed(ctx.sessionState().sessionToken());
        } else {
            return Result.failure();
        }
    }

    @Mapping("/i18n")
    public Result changeLocale(Context ctx, String l) {
        ctx.cookieSet("SOLON.LOCALE", l);
        return Result.succeed("ok");
    }


}
