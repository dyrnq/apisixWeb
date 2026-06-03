package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Credential;
import com.dyrnq.controller.PageResult;
import com.dyrnq.service.op.Factory;
import java.util.List;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping("api/credential")
@Controller
public class CredentialController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(CredentialController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            com.dyrnq.service.op.Op<Credential> op = new com.dyrnq.service.op.CredentialOp();
            op.del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String username, String id, String rawData) {
        try {
            getAdminClient().putCredentialRaw(username, id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public Result query(Context ctx) {
        try {
            List<Credential> result = getAdminClient().listAllCredentials();
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("code", 0);
            map.put("description", "");
            map.put("data", result);
            map.put("total", result.size());
            return Result.succeed(map);
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }
}
