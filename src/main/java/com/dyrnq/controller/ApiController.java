package com.dyrnq.controller;

import com.apiseven.apisix.common.exception.ApisixSDKExcetion;
import com.apiseven.apisix.common.profile.Credential;
import com.apiseven.apisix.common.profile.DefaultCredential;
import com.apiseven.apisix.common.profile.DefaultProfile;
import com.apiseven.apisix.common.profile.Profile;
import com.dyrnq.apisix.AdminClient;
import io.jsonwebtoken.Claims;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping("api")
@Controller
public class ApiController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(ApiController.class);




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

    @Mapping("secret")
    public Result secret(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listSecrets());
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


}
