package com.dyrnq.controller.api;

import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.ManifestVerMapper;
import com.dyrnq.model.ManifestVer;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Path;
import org.noear.solon.core.handle.Context;
import org.noear.wood.IPage;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.ext.Act1;

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


}
