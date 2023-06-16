package com.dyrnq.controller.api;

import cn.hutool.core.util.PageUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.CaMapper;
import com.dyrnq.dso.CertMapper;
import com.dyrnq.model.Ca;
import com.dyrnq.model.Cert;
import com.dyrnq.service.CertService;
import com.dyrnq.utils.CertUtils;
import com.dyrnq.utils.X509Holder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Mapping("api/cert")
@Controller
public class CertController extends ApiController {
    @Inject
    CertService certService;
    @Inject
    CertMapper certMapper;
    @Inject
    CaMapper caMapper;

    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Cert> p = certMapper.selectPage(start, limit, null);
            return PageResult.succeed(p.getList(), p.getTotal());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Cert cert) {
        try {
            int ap = cert.getApproach() != null ? cert.getApproach().intValue() : 99;

            if (ap == 1) {
                //手工上传
                if (cert.getCertFile() != null) {
                    cert.setCert(IOUtils.toString(cert.getCertFile().getContent(), StandardCharsets.UTF_8));
                }
                if (cert.getKeyFile() != null) {
                    cert.setPrivateKey(IOUtils.toString(cert.getKeyFile().getContent(), StandardCharsets.UTF_8));
                }
            } else if (ap == 0) {
                //免费证书

            } else if (ap == 2) {
                //自签名
                X509Holder x509Holder = null;
                if(StringUtils.isNoneBlank(cert.getCaId())){
                    Ca ca =caMapper.selectById(cert.getCaId());
                    X509Certificate issuerCA = null;
                    PrivateKey issuerCAKey = null;
                    issuerCA  = CertUtils.loadCertificate(ca.getCert());
                    issuerCAKey = CertUtils.load(ca.getPrivateKey());
                    x509Holder = CertUtils.gen(cert.getSubject(), StringUtils.split(cert.getDomain(), ",") ,issuerCA,issuerCAKey );

                }else{
                    x509Holder = CertUtils.gen(cert.getSubject(), StringUtils.split(cert.getDomain(), ","));
                }


                cert.setCert(x509Holder.getCert());
                cert.setPrivateKey(x509Holder.getKey());
                cert.setEncryption(0);
            }
            certMapper.insert(cert, true);


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
                certMapper.deleteById(i);
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
            Cert inst = certMapper.selectById(id);
            return Result.succeed(inst);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, Cert cert) {
        try {
            certMapper.updateById(cert, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }


}
