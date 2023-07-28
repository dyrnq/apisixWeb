package com.dyrnq.service.applycert;

import cn.hutool.core.codec.Base64;
import com.dyrnq.HomeDir;
import com.dyrnq.cert.tencent.Tencent;
import com.dyrnq.cert.tencent.vo.*;
import com.dyrnq.model.Cert;
import com.dyrnq.service.ApplyCertificate;
import com.dyrnq.utils.CertUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Component(name = "tencentImpl")
public class TencentImpl implements ApplyCertificate {
    static Logger logger = LoggerFactory.getLogger(TencentImpl.class);

    static int SLEEP = 1200;

    @Inject
    HomeDir homeDir;

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
            SECRET_KEY = properties.getProperty("SECRET_KEY");
            SECRET_ID = properties.getProperty("SECRET_ID");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if (StringUtils.isBlank(SECRET_ID) || StringUtils.isBlank(SECRET_KEY)) {
            throw new RemoteException("SECRET_ID and SECRET_KEY must not empty!");
        }
        Tencent tencentSDK = new Tencent(SECRET_ID, SECRET_KEY);

        String domain = cert.getDomain();

        ApplyCertificateArg arg = new ApplyCertificateArg();
        arg.setDomainName(cert.getDomain());
        arg.setDvAuthMethod("DNS_AUTO");
        ApplyCertificateResult applyCertificateResult = tencentSDK.applyCertificate(arg);

        String certificateId = applyCertificateResult.getCertificateId();

//        DescribeCertificatesArg deArg = new DescribeCertificatesArg();
//        deArg.setLimit(1000);
//        deArg.setOffset(0);
//        deArg.setSearchKey(domain);
//        deArg.setCertificateStatus(new Integer[]{1});
//
//
//        List<Certificates> list = null;
//
//        //等待证书审批下发
//        while (CollectionUtil.isEmpty(list)) {
//            try {
//                DescribeCertificatesResult r = tencentSDK.describeCertificates(deArg);
//                list = r != null ? r.getCertificates() : null;
//                if (CollectionUtil.isEmpty(list)) {
//                    Thread.sleep(100);
//                }
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
//        }


        DescribeCertificateArg deArg = new DescribeCertificateArg();
        deArg.setCertificateId(certificateId);

        //等待证书审批下发

        boolean ISSUED = false;
        while (!ISSUED) {
            DescribeCertificateResult r = tencentSDK.describeCertificate(deArg);
            if (r != null && r.getStatus() != null && r.getStatus() == 1) {
                ISSUED = true;
            } else {
                sleep("waiting domain ISSUED " + domain + ", " + r.getStatusName(), SLEEP);
            }
        }

        //下载证书
        DownloadCertificateArg downloadCertificateArg = new DownloadCertificateArg();
        downloadCertificateArg.setCertificateId(certificateId);
        DownloadCertificateResult downloadCertificateResult = tencentSDK.downloadCertificate(downloadCertificateArg);
        byte[] decodedBytes = Base64.decode(downloadCertificateResult.getContent());
        String outputPath = StringUtils.joinWith(File.separator, homeDir.getTmpAbsolutePath(), domain + ".zip");
        FileUtils.writeByteArrayToFile(new File(outputPath), decodedBytes);

        try (ZipFile zipFile = new ZipFile(outputPath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    //logger.info("zip files: " + entry.getName());
                    if (StringUtils.equalsIgnoreCase(entry.getName(), domain + ".key")) {
                        cert.setKey(IOUtils.toString(zipFile.getInputStream(entry), StandardCharsets.UTF_8));
                    } else if (StringUtils.equalsIgnoreCase(entry.getName(), domain + ".pem")) {
                        cert.setCert(IOUtils.toString(zipFile.getInputStream(entry), StandardCharsets.UTF_8));
                    }
                }
            }
        }
        if (cert.getCert() != null) {
            X509Certificate x509Cert = CertUtils.loadCertificate(cert.getCert());
            cert.setNotAfter(x509Cert.getNotAfter().getTime());
            cert.setNotBefore(x509Cert.getNotBefore().getTime());
            cert.setSubject(x509Cert.getSubjectDN().toString());
        }

    }

    @Override
    public void http(Cert cert) throws CertificateException, IOException {
        //没有找到相关接口实现这个HTTP
        throw new RuntimeException("not support");
    }

}
