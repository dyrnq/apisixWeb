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
    public JsonResult getToken(String name, String pass) {

        // 用户名密码
//        Admin admin = adminService.login(name, pass);
//        if (admin == null) {
//            return renderError(m.get("loginStr.backError2")); // 用户名密码错误
//        }
//        if (!admin.getApi()) {
//            return renderError(m.get("loginStr.backError7")); // 无接口权限
//        }

        Map<String, String> map = new HashMap<String, String>();
//        map.put("token", adminService.makeToken(admin.getId()));

        return renderSuccess(map);
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
