package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.PluginMetadata;
import com.dyrnq.service.op.Factory;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public Result list(Context ctx) {
        try {
            List<Map> list = getAdminClient().listPlugins();

            Map<String, Boolean> pluginMetadata = new HashMap<String, Boolean>();

            for (Map map : list) {
                String id = String.valueOf(map.get("id"));
                try {
                    getAdminClient().getPluginMetadata(id);
                    pluginMetadata.put(id, true);
                } catch (Exception e) {
                    pluginMetadata.put(id, false);
                }
            }


            return Result.succeed(pluginMetadata);
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("has")
    public Result has(Context ctx, String id) {
        try {
            getAdminClient().getPluginMetadata(id);
            return Result.succeed(true);
        } catch (Exception e) {
            return Result.succeed(false);
        }
    }

    @Mapping("del")
    public Result del(Context ctx, String id) {
        try {
            Factory.create(PluginMetadata.class).del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

}
