package com.dyrnq.controller.api;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSONUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.DeployMapper;
import com.dyrnq.dso.ManifestMapper;
import com.dyrnq.dso.ManifestVerMapper;
import com.dyrnq.model.Deploy;
import com.dyrnq.model.Manifest;
import com.dyrnq.model.ManifestVer;
import com.dyrnq.service.op.Factory;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.ext.Act1;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapping("api/manifest")
@Controller
public class ManifestController extends ApiController {

    @Inject
    ManifestMapper manifestMapper;

    @Inject
    ManifestVerMapper manifestVerMapper;

    @Inject
    DeployMapper deployMapper;

    public Class get(String name) {
        try {
            if(StringUtils.contains(name,".")){
                Class.forName(name);
            }
            return Class.forName("com.dyrnq.apisix.domain."+name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Manifest> p = manifestMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Manifest manifest) {
        try {
            manifestMapper.insert(manifest, true);

            ManifestVer manifestVer = new ManifestVer();
            manifestVer.setId(manifest.getId());
            manifestVer.setVer(System.currentTimeMillis());
            manifestVer.setContent(manifest.getContent());
            manifestVerMapper.insert(manifestVer, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("get")
    public Result get(Context ctx, String id) {
        try {
            Manifest manifest = manifestMapper.selectById(id);

            Act1<MapperWhereQ> condition = mapperWhereQ -> {
                mapperWhereQ.whereEq("id", id).orderByDesc("ver");
            };

            List<ManifestVer> list = manifestVerMapper.selectList(condition);
            if (list != null && list.size() > 0) {
                ManifestVer ver = list.get(0);
                manifest.setContent(ver.getContent());
                manifest.setVer(ver.getVer());
            }

            return Result.succeed(manifest);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, Manifest manifest) {
        try {
            manifestMapper.updateById(manifest, true);

            ManifestVer manifestVer = new ManifestVer();
            manifestVer.setId(manifest.getId());
            manifestVer.setVer(System.currentTimeMillis());
            manifestVer.setContent(manifest.getContent());
            manifestVerMapper.insert(manifestVer, true);

            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("deploy")
    public Result deploy(Context ctx, String id) {
        try {
            Manifest manifest = manifestMapper.selectById(id);

            Act1<MapperWhereQ> condition = mapperWhereQ -> {
                mapperWhereQ.whereEq("id", id).orderByDesc("ver");
            };

            List<ManifestVer> list = manifestVerMapper.selectList(condition);
            if (list != null && list.size() > 0) {
                ManifestVer ver = list.get(0);
                manifest.setContent(ver.getContent());
                manifest.setVer(ver.getVer());

                Yaml yaml = new Yaml();


//        Gson gson = new GsonBuilder()
//                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
//                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
//                .setPrettyPrinting()
//                .disableHtmlEscaping().create();

                Iterable<Object> blocks = yaml.loadAll(new StringReader(ver.getContent()));

                for (Object block : blocks) {
                    //System.out.println(BeanUtil.getProperty(block,"kind"));
                    if (block instanceof Map) {
                        Map<String, ?> map = (Map) block;
                        if (map.containsKey("kind")) {
                            String className = map.get("kind").toString();
                            String _id = map.get("id").toString();
                            Class cz = get(className);
                            String json = JSONUtil.toJsonStr(map);
                            Factory.create(className).putRaw(this.getAdminClient(), _id, json);
                        }
                        //System.out.println("Block: " + block.toString());
                    }
                }
                Deploy deploy = new Deploy();
                deploy.setId(UUID.randomUUID(true).toString());
                deploy.setInsertTime(new Date());
                deploy.setManifestVer(ver.getVer());
                deploy.setManifestId(id);

                deployMapper.insert(deploy,true);

            }



            return Result.succeed(manifest);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

}
