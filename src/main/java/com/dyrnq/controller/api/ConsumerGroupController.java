package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.ConsumerGroup;
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
import java.util.Map;

@Mapping("api/consumerGroup")
@Controller
public class ConsumerGroupController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(ConsumerGroupController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            Factory.create(ConsumerGroup.class).del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putConsumerGroupRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public PageResult query(Context ctx, String page, String limit,String label) {
        try {
            Map<String,String> qp = toMap(null,label,null);
            Multi<ConsumerGroup> rsp = getAdminClient().queryConsumerGroups(page, limit,qp);
            List<ConsumerGroup> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result, rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }
}
