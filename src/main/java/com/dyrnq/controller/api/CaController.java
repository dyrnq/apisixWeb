package com.dyrnq.controller.api;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.CaMapper;
import com.dyrnq.dso.CertMapper;
import com.dyrnq.model.Ca;
import com.dyrnq.service.CertService;
import com.dyrnq.utils.CertUtils;
import com.dyrnq.utils.X509Holder;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.ext.Act1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapping(path = "api/ca", produces = "application/json;charset=UTF-8")
@Controller
public class CaController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(CaController.class);
    @Inject
    CertService certService;

    @Inject
    CaMapper caMapper;
    @Inject
    CertMapper certMapper;

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Ca> p = caMapper.selectPage(start, limit, null);
            List<Ca> cerList = p.getList();
            cerList.forEach(ca -> {
                ca.setCert("***");
                ca.setKey("***");
            });
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Ca ca) {
        try {
            //自签名
            if (StringUtils.isBlank(ca.getSubject())) {
                ca.setSubject("CN=" + ca.getTitle());
            }
            if (StringUtils.isBlank(ca.getId())) {
                ca.setId(UUID.randomUUID().toString(true));
            }

            X509Holder x509Holder = CertUtils.genCA(ca.getSubject());
            ca.setCert(x509Holder.getCert());
            ca.setKey(x509Holder.getKey());
            X509Certificate x509Cert = x509Holder.getCertificate();
            ca.setNotAfter(x509Cert.getNotAfter().getTime());
            ca.setNotBefore(x509Cert.getNotBefore().getTime());
            caMapper.insert(ca, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            Act1<MapperWhereQ> condition = mapperWhereQ -> {
                mapperWhereQ.whereEq("ca_id", id[0]);
            };
            long count = certMapper.selectCount(condition);
            if (count > 0) {
                return Result.failure("data association cannot be deleted");
            } else {
                for (String i : id) {
                    caMapper.deleteById(i);
                }
                return Result.succeed("ok");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }
}
