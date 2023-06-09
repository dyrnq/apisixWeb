package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;

@Mapping("api/plugin")
@Controller
public class PluginController extends ApiController {
    @Mapping("")
    public Result plugin(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listPlugins());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }
}
