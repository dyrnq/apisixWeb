package com.dyrnq.controller;

import com.cym.utils.JsonResult;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;

import java.util.HashMap;
import java.util.Map;

@Mapping("token")
@Controller
public class TokenController extends BaseController {
//    @Inject
//    AdminService adminService;

    /**
     * 获取Token
     *
     * @param name 用户名
     * @param pass 密码
     */
    @Mapping("getToken")
    public Result getToken(Context ctx,String name, String pass) {

        // 用户名密码
//        Admin admin = adminService.login(name, pass);
//        if (admin == null) {
//            return renderError(m.get("loginStr.backError2")); // 用户名密码错误
//        }
//        if (!admin.getApi()) {
//            return renderError(m.get("loginStr.backError7")); // 无接口权限
//        }

        if ("admin".equals(name)) {
            ctx.sessionSet("user_name", name);
            ctx.sessionSet("user_pass", pass);
            ctx.sessionSet("user_id", 111);
            ctx.sessionSet("jkl", 111);

            return Result.succeed(ctx.sessionState().sessionToken());
        } else {
            return Result.failure();
        }
    }

    @Mapping("/login2")
    public Result login2(Context ctx, String name) {
        if ("noear".equals(name)) {
            ctx.sessionSet("user_name", name);

            return Result.succeed(ctx.sessionState().sessionToken());
        } else {
            return Result.failure();
        }
    }
}
