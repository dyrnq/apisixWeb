package com.dyrnq.controller.api;

import com.dyrnq.CookieName;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.controller.BaseController;
import com.dyrnq.service.BusinessLogic;
import com.dyrnq.service.op.Factory;
import com.dyrnq.service.op.Sample;
import com.google.gson.*;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Path;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
        String instId = Context.current().cookie(CookieName.NAME_INSTID, "1");
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

    @Mapping("raw")
    public Result raw(Context ctx, String cls, String id) {
        String jsonStr = "{}";
        try {
            jsonStr = g().toJson(Factory.create(cls).get(getAdminClient(), id));
        } catch (Exception Exception) {
        }
        Map map = new HashMap();
        map.put("id", id);
        map.put("rawData", jsonStr);
        return Result.succeed(map);
    }


    @Mapping("{cls}/drop")
    public Result drop(Context ctx, @Path("cls") String cls) {
        try {
            Factory.create(cls).drop(getAdminClient());
        } catch (Exception Exception) {
        }
        return Result.succeed("ok");
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

    @Mapping("{cls}/yaml")
    public Result yaml(Context ctx, @Path("cls") String cls, String... id) {
        try {
            List<Object> list = Factory.create(cls).list(getAdminClient(), id);
            return Result.succeed(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 转化不理想
     *
     * @param ctx
     * @param cls
     * @param id
     * @return
     */
    @Mapping("{cls}/yaml2")
    public Result yaml2(Context ctx, @Path("cls") String cls, String... id) {
        try {
            List<Object> list = Factory.create(cls).list(getAdminClient(), id);


            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // 设置默认流样式为块格式
            options.setProcessComments(false);
            Representer representer = new Representer(options) {
                protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
                    // if value of property is null, ignore it.
                    if (propertyValue == null) {
                        return null;
                    } else {
                        return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
                    }
                }
            };
            Yaml yaml = new Yaml(representer);

            Pattern pattern = Pattern.compile("^!!");
            StringBuilder sb = new StringBuilder();
            for (Object item : list) {
                sb.append("---").append("\n");
                String yamlStr = yaml.dump(item);
                // yaml.dumpAs(item, Tag.MAP, null);
                StringBuilder sb2 = new StringBuilder();
                for (String line : yamlStr.split("\n")) {
                    if (!pattern.matcher(line).find()) {
                        sb2.append(line).append("\n");
                    }
                }
                yamlStr = sb2.toString();
                sb.append(yamlStr);
            }
            //ctx.output(sb.toString());
            return Result.succeed(sb.toString());


        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    protected Map<String, String> toMap(String name, String label, String uri) {
        Map<String, String> qp = new HashMap<>();
        qp.put("name", name);
        qp.put("label", label);
        qp.put("uri", uri);
        return qp;
    }

}
