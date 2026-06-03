package com.dyrnq.controller.api;

import cn.hutool.core.util.StrUtil;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.StreamRoute;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.controller.PageResult;
import com.dyrnq.service.op.Factory;
import java.util.ArrayList;
import java.util.List;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Mapping("api/streamRoute")
public class StreamRouteController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(StreamRouteController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            Factory.create(StreamRoute.class).del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putStreamRouteRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public PageResult query(Context ctx, String page, String limit, String id) {
        try {

            StreamRoute route = null;
            if (StrUtil.isNotBlank(id)) {
                try {
                    route = getAdminClient().getStreamRoute(id);
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }
            if (route == null) {
                Multi<StreamRoute> rsp = getAdminClient().queryStreamRoutes(page, limit);
                List<StreamRoute> result = getAdminClient().arrangeMulti(rsp.getNodes());
                return PageResult.succeed(result, rsp.getTotal());
            } else {
                List<StreamRoute> result = new ArrayList<>();
                result.add(route);
                return PageResult.succeed(result, Integer.valueOf(1));
            }
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return PageResult.failure(e.getMessage());
        }
    }
}
