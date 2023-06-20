package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.controller.PageResult;
import com.dyrnq.service.op.Factory;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@Mapping("api/route")
public class RouteController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(RouteController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            Factory.create(Route.class).del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putRouteRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("enable")
    public Result patchRouteRawOn(Context ctx, String... id) {
        try {
            for (String i : id) {
                getAdminClient().patchRouteRaw(i, "{\"status\":1}");
            }
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("disable")
    public Result patchRouteRawOff(Context ctx, String... id) {
        try {
            for (String i : id) {
                getAdminClient().patchRouteRaw(i, "{\"status\":0}");
            }
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public PageResult query(Context ctx, String page, String limit, String name, String label, String uri) {
        try {
            Multi<Route> rsp = getAdminClient().queryRoutes(page, limit, toMap(name, label, uri));
            List<Route> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result, rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
}
