package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Consumer;
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

@Mapping("api/consumer")
@Controller
public class ConsumerController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(com.dyrnq.controller.api.ConsumerController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            Factory.create(Consumer.class).del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putConsumerRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }


    @Mapping("")
    public PageResult query(Context ctx, String page, String limit, String label) {
        try {
            Map<String, String> qp = toMap(null, label, null);
            Multi<Consumer> rsp = getAdminClient().queryConsumers(page, limit, qp);
            List<Consumer> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result, rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return PageResult.failure(e.getMessage());
        }
    }

}
