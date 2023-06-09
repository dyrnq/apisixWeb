package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.SSL;
import com.dyrnq.apisix.response.Multi;
import com.dyrnq.controller.PageResult;
import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@Mapping("api/ssl")
@Controller
public class SSLController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(SSLController.class);

    @Mapping("del")
    public Result del(Context ctx, String... id) {
        try {
            for (String i : id) {
                getAdminClient().delSSL(i);
            }
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("put")
    public Result put(Context ctx, String id, String rawData) {
        try {
            getAdminClient().putSSLRaw(id, rawData);
            return Result.succeed("ok");
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("")
    public Result query(Context ctx, String page, String limit) {
        try {
            Multi<SSL> rsp = getAdminClient().querySSLs(page, limit);
            List<SSL> result = getAdminClient().arrangeMulti(rsp.getNodes());
            return PageResult.succeed(result, rsp.getTotal());
        } catch (ApisixSDKException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Mapping("upload")
    public Result addSSLFile(Context ctx, org.noear.solon.core.handle.UploadedFile certFile, org.noear.solon.core.handle.UploadedFile keyFile, String id, String snis) {
        try {
            SSL ssl = new SSL();
            byte[] byteCert = IOUtils.toByteArray(certFile.getContent());
            byte[] byteKey = IOUtils.toByteArray(keyFile.getContent());
            ssl.setCert(IOUtils.toString(byteCert));
            ssl.setKey(IOUtils.toString(byteKey));

            if (StringUtils.isNotBlank(snis)) {
                String[] strArray = StringUtils.splitByWholeSeparator(snis, ",");
                List<String> listStr = Arrays.asList(strArray);
                ssl.setSnis(listStr);
            } else {
                X509Certificate x509Cert = CertUtils.loadCertificate(new ByteArrayInputStream((byteCert)));
                String subjectName = x509Cert.getSubjectX500Principal().getName();
                LdapName ldapName = new LdapName(subjectName);
                String cnValue = null;
                for (Rdn rdn : ldapName.getRdns()) {
                    if (rdn.getType().equalsIgnoreCase("CN")) {
                        cnValue = rdn.getValue().toString();
                        // Do something with the CN value
                        break;
                    }
                }
                Map<String, String> sniMap = new HashMap<>();
                if (cnValue != null) {
                    sniMap.put(cnValue, cnValue);
                }

                Collection<List<?>> altNames = x509Cert.getSubjectAlternativeNames();
                if (altNames != null) {
                    for (List<?> altName : altNames) {
                        if (altName.get(1) != null) {
                            String altNameStr = String.valueOf(altName.get(1));
                            sniMap.put(altNameStr, altNameStr);
                        }
                    }
                }
                List<String> sni = new ArrayList<String>();
                for (Iterator<String> it = sniMap.keySet().iterator(); it.hasNext(); ) {
                    String m = it.next();
                    sni.add(m);
                }
                ssl.setSnis(sni);
            }
            getAdminClient().putSSL(id, ssl);
            return Result.succeed("ok");
        } catch (ApisixSDKException | InvalidNameException | CertificateException | IOException e) {
            logger.error(e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

}
