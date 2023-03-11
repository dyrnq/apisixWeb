package com.dyrnq.controller;

import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.cym.utils.JsonResult;
import com.dyrnq.apisix.AdminClient;
import io.jsonwebtoken.Claims;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Mapping("token")
@Controller
public class TokenController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(TokenController.class);
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
            ctx.sessionSet(Claims.SUBJECT, name);
            ctx.sessionSet(Claims.ID, 1);
            return Result.succeed(ctx.sessionState().sessionToken());
        } else {
            return Result.failure();
        }
    }

    @Mapping("route")
    public Result route(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listRoutes());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("streamRoute")
    public Result streamRoute(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listStreamRoutes());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }


    @Mapping("upstream")
    public Result upstream(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listUpstreams());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("service")
    public Result service(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listServices());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("ssl")
    public Result ssl(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listSSLs());
        } catch (ApisixSDKExcetion e) {
            return Result.failure(e.getMessage());
        }
    }


    private AdminClient getAdminClient(){
        String url ="192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile (url ,"", c);
        AdminClient client =new AdminClient(p);
        return client;
    }

//    @Mapping("/login2")
//    public Result login2(Context ctx, String name) {
//        if ("noear".equals(name)) {
//            ctx.sessionSet("user_name", name);
//
//            return Result.succeed(ctx.sessionState().sessionToken());
//        } else {
//            return Result.failure();
//        }
//    }
}
