package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.GlobalRule;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.controller.PageResult;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Mapping("api/globalRule")
@Controller
public class GlobalRuleController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(GlobalRuleController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            for (String i : id) {
                getAdminClient().delGlobalRule(i);
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
            getAdminClient().putGlobalRuleRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public Result query(Context ctx, String page, String limit) {
        try {
            Multi<GlobalRule> rsp = getAdminClient().queryGlobalRules(page, limit);
            List<GlobalRule> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result, rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

}
