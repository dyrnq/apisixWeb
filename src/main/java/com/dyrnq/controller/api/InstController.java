package com.dyrnq.controller.api;

import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.InstMapper;
import com.dyrnq.model.Inst;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapping("api/inst")
@Controller
public class InstController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(InstController.class);
    @Inject
    InstMapper instMapper;

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Inst> p = instMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Inst inst) {
        try {
            instMapper.insert(inst, true);
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
                instMapper.deleteById(i);
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
            Inst inst = instMapper.selectById(id);
            return Result.succeed(inst);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, Inst inst) {
        try {
            instMapper.updateById(inst, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("list")
    public Result list(Context ctx) {
        try {
            List<Inst> p = instMapper.selectList(null);
            return Result.succeed(p);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("dropdown")
    public Result dropdown(Context ctx) {
        try {
            List<Inst> p = instMapper.selectList(null);
            List<Map<String, String>> o = new ArrayList<>();
            for (Inst inst : p) {
                Map<String, String> m = new HashMap<>();
                m.put("title", inst.getName());
                m.put("id", inst.getId());
                o.add(m);
            }
            return Result.succeed(o);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("drop")
    public Result drop(Context ctx, String id) {
        try {
            businessLogic.drop(id);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

}
