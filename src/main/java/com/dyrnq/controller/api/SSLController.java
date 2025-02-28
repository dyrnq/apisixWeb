package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Client;
import com.dyrnq.apisix.domain.SSL;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.controller.PageResult;
import com.dyrnq.service.op.Factory;
import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.solon.core.handle.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mapping(path = "api/ssl", produces = "application/json;charset=UTF-8")
@Controller
public class SSLController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(SSLController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            Factory.create(SSL.class).del(getAdminClient(), id);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putSSLRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("enable")
    public Result patchSSLRawOn(Context ctx, String... id) {
        try {
            for (String i : id) {
                getAdminClient().patchSSLRaw(i, "{\"status\":1}");
            }
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("disable")
    public Result patchSSLRawOff(Context ctx, String... id) {
        try {
            for (String i : id) {
                getAdminClient().patchSSLRaw(i, "{\"status\":0}");
            }
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public Result query(Context ctx, String page, String limit, String label) {
        try {
            Map<String, String> pq = toMap(null, label, null);
            Multi<SSL> rsp = getAdminClient().querySSLs(page, limit, pq);
            List<SSL> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result, rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("upload")
    public Result addSSLFile(Context ctx, UploadedFile certFile, UploadedFile keyFile, String id, String snis, String type, UploadedFile caCertFile) {
        try {
            SSL ssl = new SSL();
            byte[] byteCert = IOUtils.toByteArray(certFile.getContent());
            byte[] byteKey = IOUtils.toByteArray(keyFile.getContent());
            ssl.setCert(new String(byteCert, Charset.defaultCharset()));
            ssl.setKey(new String(byteKey, Charset.defaultCharset()));

            ssl.setType(type);
            if (StringUtils.isNotBlank(snis)) {
                String[] strArray = StringUtils.splitByWholeSeparator(snis, ",");
                List<String> listStr = Arrays.asList(strArray);
                ssl.setSnis(listStr);
            } else {
                X509Certificate x509Cert = CertUtils.loadCertificate(new ByteArrayInputStream((byteCert)));
                String[] sniArray = CertUtils.extractSNI(x509Cert);
                ssl.setSnis(Arrays.asList(sniArray));
            }
            if (StringUtils.equalsIgnoreCase("client", type) && caCertFile != null) {
                Client c = new Client();
                c.setCa(new String(IOUtils.toByteArray(caCertFile.getContent()), Charset.defaultCharset()));
                ssl.setClient(c);
            }
            getAdminClient().putSSL(id, ssl);
            return Result.succeed("ok");
        } catch (ApisixSDKException | InvalidNameException | CertificateException | IOException e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }

}
