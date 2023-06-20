package com.dyrnq.controller.api;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;
import com.dyrnq.controller.PageResult;
import com.dyrnq.dso.CertMapper;
import com.dyrnq.model.Cert;
import com.dyrnq.service.CertService;
import enumeration.Approach;
import enumeration.Challenge;
import enumeration.Encryption;
import enumeration.Supplier;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Path;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.wood.IPage;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

@Mapping("api/cert")
@Controller
public class CertController extends ApiController {
    @Inject
    CertService certService;
    @Inject
    CertMapper certMapper;


    @Mapping("dnsapi")
    public Result dnsapi() {
        try {
            return Result.succeed(certService.getAcmeshDnsapi());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }


    @Mapping("")
    public PageResult query(Context ctx, int page, int limit) {
        try {
            int start = PageUtil.getStart(page - 1, limit);
            IPage<Cert> p = certMapper.selectPage(start, limit, null);

            List<Cert> cerList = p.getList();
            cerList.forEach(cert -> {
                cert.setCert("***");
                cert.setKey("***");

                for (Encryption e : Encryption.values()) {
                    if (cert.getEncryption() != null && cert.getEncryption() == e.getId()) {
                        cert.setEncryptionDisplay(e.getName());
                        break;
                    }
                }
                for (Approach e : Approach.values()) {
                    if (cert.getApproach() != null && cert.getApproach() == e.getId()) {
                        cert.setApproachDisplay(e.getName());
                        break;
                    }
                }
                for (Supplier e : Supplier.values()) {
                    if (cert.getSupplier() != null && cert.getSupplier() == e.getId()) {
                        cert.setSupplierDisplay(e.getName());
                        break;
                    }
                }
                for (Challenge e : Challenge.values()) {
                    if (cert.getChallenge() != null && cert.getChallenge() == e.getId()) {
                        cert.setChallengeDisplay(e.getName());
                        break;
                    }
                }


            });
            return PageResult.succeed(cerList, p.getTotal());

        } catch (Exception e) {
            logger.error(e.getMessage());
            return PageResult.failure(e.getMessage());
        }
    }

    @Mapping("add")
    public Result add(Context ctx, Cert cert) {
        try {
            if (StringUtils.isBlank(cert.getId())) {
                cert.setId(UUID.randomUUID().toString(true));
            }
            if (cert.getApproach() == Approach.manual.getId()) {
                certService.manual(cert);
            }
            //界面选中马上申请才去申请
            if (cert.getIssue() != null && cert.getIssue().intValue() == 1) {
                certService.issue(cert);
            }
            certMapper.insert(cert, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("issue")
    public Result issue(Context ctx, String id) {
        try {
            Cert cert = certMapper.selectById(id);
            certService.issue(cert);
            certMapper.updateById(cert, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("renew")
    public Result renew(Context ctx, String id) {
        try {
            Cert cert = certMapper.selectById(id);
            certService.renew(cert);
            certMapper.updateById(cert, true);
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
            Cert cert = certMapper.selectById(id);
            cert.setCert("***");
            cert.setKey("***");
            return Result.succeed(cert);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("update")
    public Result update(Context ctx, Cert cert) {
        try {
            if (cert.getApproach() == Approach.manual.getId()) {
                certService.manual(cert);
            }
            certMapper.updateById(cert, true);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("dump/{id}")
    public void dump(Context ctx, @Path("id") String id) {
        try {
            Cert cert = certMapper.selectById(id);
            if (cert.getCert() != null) {
                java.nio.file.Path tempFile = Files.createTempFile(null, ".tmp");
                FileUtils.write(new File(tempFile.toString()), cert.getCert(), Charset.defaultCharset());
                String cmd = "openssl x509 -noout -text -in " + tempFile;
                if (SystemUtil.getOsInfo().isWindows()) {
                    cmd = "keytool -printcert -file " + tempFile;
                }
                String rs = RuntimeUtil.execForStr(cmd);
                FileUtils.forceDelete(tempFile.toFile());
                ctx.output(rs);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            ctx.output(e.getMessage());
        }
    }

}
