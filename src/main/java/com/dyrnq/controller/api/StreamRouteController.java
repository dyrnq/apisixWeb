package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.StreamRoute;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.controller.PageResult;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@Mapping("api/streamRoute")
public class StreamRouteController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(StreamRouteController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            for (String i : id) {
                getAdminClient().delStreamRoute(i);
            }
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putStreamRouteRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public PageResult query(Context ctx, String page, String limit) {
        try {
            Multi<StreamRoute> rsp = getAdminClient().queryStreamRoutes(page, limit);
            List<StreamRoute> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result, rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

}
