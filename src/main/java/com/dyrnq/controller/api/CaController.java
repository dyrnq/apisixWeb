package com.dyrnq.controller.api;

import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.CaMapper;
import com.dyrnq.model.Ca;
import com.dyrnq.service.CertService;
import com.dyrnq.utils.CertUtils;
import com.dyrnq.utils.X509Holder;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapping("api/ca")
@Controller
public class CaController extends ApiController {

    @Inject
    CertService certService;

    @Inject
    CaMapper caMapper;


    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Ca> p = caMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Ca ca) {
        try {
            //自签名
            X509Holder x509Holder = CertUtils.genCA(ca.getSubject());
            ca.setCert(x509Holder.getCert());
            ca.setPrivateKey(x509Holder.getKey());
            caMapper.insert(ca, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("dropdown")
    public Result dropdown(Context ctx) {
        try {
            List<Ca> p = caMapper.selectList(null);
            List<Map<String, String>> o = new ArrayList<>();
            for (Ca inst : p) {
                Map<String, String> m = new HashMap<>();
                m.put("title", inst.getTitle());
                m.put("id", inst.getId());
                o.add(m);
            }
            return Result.succeed(o);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }
}
