package com.dyrnq.controller.api;

import cn.hutool.core.util.StrUtil;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Upstream;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.controller.PageResult;
import com.dyrnq.service.op.Factory;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Controller
@Mapping("api/upstream")
public class UpstreamController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(UpstreamController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            Factory.create(Upstream.class).del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putUpstreamRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public PageResult query(Context ctx, String page, String limit, String id, String name) {
        try {
            Upstream route = null;
            if (StrUtil.isNotBlank(id)) {
                try {
                    route = getAdminClient().getUpstream(id);
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }

            }
            if (route == null) {
                Multi<Upstream> rsp = getAdminClient().queryUpstreams(page, limit, toMap(name, null, null));
                List<Upstream> result = getAdminClient().arrangeMulti(rsp.getNodes());
                return PageResult.succeed(result, rsp.getTotal());
            } else {
                List<Upstream> result = new ArrayList<>();
                result.add(route);
                return PageResult.succeed(result, new Integer(1));
            }

        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return PageResult.failure(e.getMessage());
        }
    }

}
