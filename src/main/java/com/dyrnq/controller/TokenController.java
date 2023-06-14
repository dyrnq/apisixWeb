package com.dyrnq.controller;

import com.dyrnq.model.User;
import com.dyrnq.service.BusinessLogic;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
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
        try {
            User user = businessLogic.login(name, pass);
            ctx.sessionSet(Claims.SUBJECT, user.getName());
            return Result.succeed(ctx.sessionState().sessionToken());
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("/i18n")
    public Result changeLocale(Context ctx, String l) {
        ctx.cookieSet("SOLON.LOCALE", l);
        return Result.succeed("ok");
    }
    @Mapping("/cap")
    public void getCode(Context ctx) throws Exception {
        ctx.headerAdd("Pragma", "No-cache");
        ctx.headerAdd("Cache-Control", "no-cache");
        ctx.headerAdd("Expires", "0");
        ctx.contentType("image/gif");

        SpecCaptcha specCaptcha = new SpecCaptcha(100, 40, 4);
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        ctx.sessionSet("captcha", specCaptcha.text().toLowerCase());
        specCaptcha.out(ctx.outputStream());
    }
}
