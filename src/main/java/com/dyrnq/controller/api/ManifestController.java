package com.dyrnq.controller.api;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.DeployMapper;
import com.dyrnq.dso.ManifestMapper;
import com.dyrnq.model.Manifest;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping("api/manifest")
@Controller
public class ManifestController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(ManifestController.class);

    @Inject
    ManifestMapper manifestMapper;

    @Inject
    DeployMapper deployMapper;

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Manifest> p = manifestMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Manifest manifest) {
        try {
            if (StringUtils.isBlank(manifest.getId())) {
                manifest.setId(UUID.randomUUID(true).toString());
            }
            manifestMapper.insert(manifest, true);

            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            for (String i : id) {
                manifestMapper.deleteById(i);
            }
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("get")
    public Result get(Context ctx, String id) {
        try {
            Manifest manifest = manifestMapper.selectById(id);
            return Result.succeed(manifest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, Manifest manifest) {
        try {
            manifestMapper.updateById(manifest, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("deploy")
    public Result deploy(Context ctx, String id) {
        try {
            // 委托给 DeployController 的 deploy 逻辑
            com.dyrnq.apisix.AdminClient client = businessLogic.getAdminClient();
            Manifest manifest = manifestMapper.selectById(id);
            if (manifest == null || StringUtils.isBlank(manifest.getContent())) {
                return Result.failure("manifest not found or content is empty");
            }
            org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml(
                    new org.yaml.snakeyaml.constructor.SafeConstructor(new org.yaml.snakeyaml.LoaderOptions()));
            Iterable<Object> blocks = yaml.loadAll(new java.io.StringReader(manifest.getContent()));
            for (Object block : blocks) {
                if (block instanceof java.util.Map) {
                    java.util.Map<String, ?> map = (java.util.Map) block;
                    if (map.containsKey("kind")) {
                        String className = map.get("kind").toString();
                        String _id = map.get("id").toString();
                        String json =
                                cn.hutool.json.JSONUtil.toJsonStr(cn.hutool.core.map.MapUtil.removeAny(map, "kind"));
                        com.dyrnq.service.op.Factory.create(className).putRaw(client, _id, json);
                    }
                }
            }
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }
}
