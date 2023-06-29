package com.dyrnq.controller.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.ManifestVerMapper;
import com.dyrnq.model.Ca;
import com.dyrnq.model.Inst;
import com.dyrnq.model.ManifestVer;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.ext.Act1;

import java.util.*;

@Mapping("api/manifestVer")
@Controller
public class ManifestVerController extends ApiController {

    @Inject
    ManifestVerMapper manifestVerMapper;

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit, String id) {
        try {
            int start = PageUtil.getStart(page - 1, limit);

            Act1<MapperWhereQ> condition = mapperWhereQ -> {
                mapperWhereQ.whereEq("id", id).orderByDesc("ver");
            };

            IPage<ManifestVer> p = manifestVerMapper.selectPage(start, limit, condition);

            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("del")
    public Result del(Context ctx, String id, Long ver) {
        try {
            Act1<MapperWhereQ> condition = mapperWhereQ -> {
                mapperWhereQ.whereEq("id", id).andEq("ver",ver);
            };
            manifestVerMapper.delete(condition);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }
    @Mapping("dropdown")
    public Result dropdown(Context ctx,String id) {
        try {

            Act1<MapperWhereQ> condition = mapperWhereQ -> {
                mapperWhereQ.whereEq("id", id).orderByDesc("ver");
            };
            List<ManifestVer> list = manifestVerMapper.selectList(condition);
            List<Map<String, String>> o = new ArrayList<>();
            for(ManifestVer item : list){
                Map<String,String> m= new HashMap<>();
                m.put("title",DateUtil.format(new Date(item.getVer()),"yyyy-MM-dd HH:mm:ss"));
                m.put("id",item.getVer().toString());
                o.add(m);
            }
            return Result.succeed(o);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

}
