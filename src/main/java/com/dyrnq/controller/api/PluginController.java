package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping(path = "api/plugin", produces = "application/json;charset=UTF-8")
@Controller
public class PluginController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(PluginController.class);

    @Mapping("")
    public Result plugin(Context ctx) {
        try {
            return Result.succeed(getAdminClient().listPlugins());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }
}
