package com.dyrnq.controller;

import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.dso.UserMapper;
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

import java.util.HashMap;
import java.util.Map;

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
    public Result getToken(Context ctx,String name, String pass) {

        User user = businessLogic.login(name,pass);
//        //用户名密码
//        Admin admin = adminService.login(name, pass);
//        if (admin == null) {
//            return renderError(m.get("loginStr.backError2")); // 用户名密码错误
//        }
//        if (!admin.getApi()) {
//            return renderError(m.get("loginStr.backError7")); // 无接口权限
//        }

        if(user!=null){
            ctx.sessionSet(Claims.SUBJECT, user.getName());
            return Result.succeed(ctx.sessionState().sessionToken());
        } else {
            return Result.failure();
        }
    }


}
