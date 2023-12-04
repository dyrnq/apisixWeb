package com.dyrnq.controller.api;

import com.dyrnq.CookieName;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.controller.BaseController;
import com.dyrnq.service.BusinessLogic;
import com.dyrnq.service.op.Factory;
import com.dyrnq.service.op.Sample;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Path;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapping("api")
@Controller
public class ApiController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Inject
    BusinessLogic businessLogic;

    protected AdminClient getAdminClient() throws ApisixSDKException {
        return businessLogic.getAdminClient();
    }

    public String instId() {
        String instId = Context.current().cookieOrDefault(CookieName.NAME_INSTID, "1");
        return instId;
    }


    private Gson g() {
        Gson gson = new GsonBuilder()
                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return "id".equals(f.getName()) || "createTime".equals(f.getName()) || "updateTime".equals(f.getName()); // 如果是特殊字段，则排除
// 其他字段都保留
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson;
    }

    @Mapping("{cls}/yaml")
    public Result yaml(Context ctx, @Path("cls") String cls, String... id) {
        try {
            List<Object> list = Factory.create(cls).list(getAdminClient(), id);
            return Result.succeed(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("raw")
    public Result raw(Context ctx, String cls, String id) {
        Map map = new HashMap();
        map.put("id", id);
        try {
            String jsonStr = g().toJson(Factory.create(cls).get(getAdminClient(), id));
            map.put("rawData", jsonStr);
            return Result.succeed(map);
        } catch (Exception e) {
            if("pluginMetadata".equalsIgnoreCase(cls) && StringUtils.contains(e.getMessage(),"Key not found")){
                map.put("rawData", "");
                //logger.error(e.getMessage(), e);
                return Result.succeed(map);
            }
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }


    @Mapping("{cls}/drop")
    public Result drop(Context ctx, @Path("cls") String cls) {
        try {
            Factory.create(cls).drop(getAdminClient());
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }


    @Mapping("{cls}/sample")
    public Result sample(Context ctx, @Path("cls") String cls) {
        String jsonStr = "{}";
        Sample sample = Factory.createSample(cls);
        jsonStr = g().toJson(sample.sample());
        Map map = new HashMap();
        map.put("rawData", jsonStr);
        return Result.succeed(map);
    }


    protected Map<String, String> toMap(String name, String label, String uri) {
        Map<String, String> qp = new HashMap<>();
        qp.put("name", name);
        qp.put("label", label);
        qp.put("uri", uri);
        return qp;
    }

}
