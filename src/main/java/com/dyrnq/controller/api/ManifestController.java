package com.dyrnq.controller.api;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.DeployMapper;
import com.dyrnq.dso.ManifestMapper;
import com.dyrnq.dso.ManifestVerMapper;
import com.dyrnq.model.Manifest;
import com.dyrnq.model.ManifestVer;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.ext.Act1;

import java.util.List;

@Mapping("api/manifest")
@Controller
public class ManifestController extends ApiController {

    @Inject
    ManifestMapper manifestMapper;

    @Inject
    ManifestVerMapper manifestVerMapper;

    @Inject
    DeployMapper deployMapper;


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
            if (StringUtils.isBlank(manifest.getId())) {
                manifest.setId(UUID.randomUUID(true).toString());
            }
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
                Act1<MapperWhereQ> condition = mapperWhereQ -> {
                    mapperWhereQ.whereEq("id", id);
                };
                manifestVerMapper.delete(condition);
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
        throw new RuntimeException("not support");
    }

}
