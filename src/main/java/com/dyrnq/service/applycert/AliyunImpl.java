package com.dyrnq.service.applycert;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.plugins.ResponseRewrite;
import com.dyrnq.cert.aliyun.Aliyun;
import com.dyrnq.cert.aliyun.DomainUtils;
import com.dyrnq.cert.aliyun.vo.DescribeCertificateStateResult;
import com.dyrnq.model.Cert;
import com.dyrnq.service.ApplyCertificate;
import com.dyrnq.service.BusinessLogic;
import com.dyrnq.utils.CertUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = "aliyunImpl")
public class AliyunImpl implements ApplyCertificate {
    static Logger logger = LoggerFactory.getLogger(AliyunImpl.class);
    static int SLEEP = 1200;

    @Inject
    BusinessLogic businessLogic;

    public static void sleep(String log, int m) {
        try {
            logger.info(log + ", will Thread.sleep(" + m + ")");
            Thread.sleep(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dns(Cert cert) throws CertificateException, IOException {
        Properties properties = new Properties();
        InputStream inputStream = new ByteArrayInputStream((cert.getAux() != null ? cert.getAux() : "").getBytes());
        String SECRET_KEY = null;
        String SECRET_ID = null;
        try {
            properties.load(inputStream);
            SECRET_KEY = properties.getProperty("ACCESS_KEY_SECRET");
            SECRET_ID = properties.getProperty("ACCESS_KEY_ID");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if (StringUtils.isBlank(SECRET_ID) || StringUtils.isBlank(SECRET_KEY)) {
            throw new RemoteException("ACCESS_KEY_ID and ACCESS_KEY_SECRET must not empty!");
        }
        Aliyun aliyunSDK = new Aliyun(SECRET_ID, SECRET_KEY);

        String domain = cert.getDomain();
        String baseDomain = DomainUtils.base(domain);

        Long orderId = aliyunSDK.createCertificateForPackageRequest(domain, "DNS");

        String recordDomain = null;
        String recodeValue = null;
        String recodeType = null;

        while (recordDomain == null) {
            try {
                DescribeCertificateStateResult result = aliyunSDK.describeCertificateState(orderId);
                recordDomain = result.getRecordDomain();

                if (recordDomain == null) {
                    sleep("recordDomain is null, will get recordDomain", SLEEP);
                    continue;
                }

                recodeValue = result.getRecordValue();
                recodeType = result.getRecordType();

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        // 此处要将拿到的recordDomain转化为dns中的rr记录,比如 hello.dyrnq.com, hello是RR记录,dyrnq.com为域名
        String rr = StringUtils.replace(recordDomain, "." + baseDomain, "");
        // 插入域名校验记录，腾讯云那边有AUTO_DNS，不需要这一步
        aliyunSDK.addDomainRecord(baseDomain, rr, recodeType, recodeValue);

        //        List<CertificateOrder> list = null;
        //        while (CollectionUtil.isEmpty(list)) {
        //            try {
        //                list = aliyunSDK.listUserCertificateOrder(domain, "ISSUED");
        //                if (CollectionUtil.isEmpty(list)) {
        //                    Thread.sleep(100);
        //                }
        //            } catch (Exception e) {
        //                logger.error(e.getMessage());
        //            }
        //        }
        //
        //        for (CertificateOrder obj : list) {
        //            DescribeCertificateStateResult result = aliyunSDK.describeCertificateState(obj.getOrderId());
        //            if (result != null) {
        //                cert.setKey(result.getPrivateKey());
        //                cert.setCert(result.getCertificate());
        //                break;
        //            }
        //        }

        post(cert, domain, aliyunSDK, orderId);
    }

    private void post(Cert cert, String domain, Aliyun aliyunSDK, Long orderId)
            throws CertificateException, IOException {
        // 等待证书审批下发,用orderId去查询,不能根据domain去模糊匹配,那样会查询到多个同时ISSUED的证书,不好判断哪个是新的
        String certificateTxt = null;
        String keyTxt = null;
        while (certificateTxt == null) {
            try {
                DescribeCertificateStateResult result = aliyunSDK.describeCertificateState(orderId);
                certificateTxt = result.getCertificate();
                keyTxt = result.getPrivateKey();
                if (certificateTxt == null) {
                    sleep("waiting domain ISSUED " + domain + ", " + result.getType(), SLEEP);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        cert.setKey(keyTxt);
        cert.setCert(certificateTxt);

        if (cert.getCert() != null) {
            X509Certificate x509Cert = CertUtils.loadCertificate(cert.getCert());
            cert.setNotAfter(x509Cert.getNotAfter().getTime());
            cert.setNotBefore(x509Cert.getNotBefore().getTime());
            cert.setSubject(x509Cert.getSubjectDN().toString());
        }
    }

    @Override
    public void http(Cert cert) throws CertificateException, IOException {
        Properties properties = new Properties();
        InputStream inputStream = new ByteArrayInputStream((cert.getAux() != null ? cert.getAux() : "").getBytes());
        String SECRET_KEY = null;
        String SECRET_ID = null;
        try {
            properties.load(inputStream);
            SECRET_KEY = properties.getProperty("ACCESS_KEY_SECRET");
            SECRET_ID = properties.getProperty("ACCESS_KEY_ID");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if (StringUtils.isBlank(SECRET_ID) || StringUtils.isBlank(SECRET_KEY)) {
            throw new RemoteException("ACCESS_KEY_ID and ACCESS_KEY_SECRET must not empty!");
        }
        Aliyun aliyunSDK = new Aliyun(SECRET_ID, SECRET_KEY);

        String domain = cert.getDomain();

        Long orderId = aliyunSDK.createCertificateForPackageRequest(domain, "FILE");

        String fileContent = null;
        String uri = null;

        while (fileContent == null) {
            try {
                DescribeCertificateStateResult result = aliyunSDK.describeCertificateState(orderId);
                fileContent = result.getContent();

                if (fileContent == null) {
                    sleep("fileContent is null, will get fileContent", SLEEP);
                    continue;
                }
                uri = result.getUri();

                // 使用当前apisix instance充当80端口验证
                AdminClient client = businessLogic.getAdminClient();
                Route r = new Route();
                List<String> hosts = new ArrayList<>();
                hosts.add(domain);
                r.setHosts(hosts);
                r.setName(domain + " aliyun-file-challenge");
                r.setUri(uri);
                Map<String, Object> map = new HashMap<>();
                ResponseRewrite responseRewrite = new ResponseRewrite();
                responseRewrite.body = fileContent;
                responseRewrite.statusCode = 200;
                map.put(ResponseRewrite.PLUGIN_NAME, responseRewrite);
                r.setPlugins(map);
                client.putRoute(domain, r);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        post(cert, domain, aliyunSDK, orderId);
    }
}
