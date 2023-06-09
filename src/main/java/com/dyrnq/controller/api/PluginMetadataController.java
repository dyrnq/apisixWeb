package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping("api/pluginMetadata")
@Controller
public class PluginMetadataController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(PluginMetadataController.class);

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putPluginMetadataRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }
}
