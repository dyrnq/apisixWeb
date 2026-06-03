package com.dyrnq.service.applycert;

import cn.hutool.core.io.FileUtil;
import com.dyrnq.HomeDir;
import com.dyrnq.cert.acme.AcmeClient;
import com.dyrnq.cert.acme.AcmeshCmd;
import com.dyrnq.model.Cert;
import com.dyrnq.service.ApplyCertificate;
import com.dyrnq.utils.CertUtils;
import enumeration.Encryption;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = "acmeImpl")
public class AcmeImpl implements ApplyCertificate {
    static Logger logger = LoggerFactory.getLogger(AcmeImpl.class);

    @Inject
    HomeDir homeDir;

    @Inject
    AcmeshCmd acmeshCmd;

    @Inject
    AcmeClient acmeClient;

    @Override
    public void dns(Cert cert) throws CertificateException, IOException {
        String acmeHome = homeDir.getAcmeHome();
        acmeshCmd.execCMD(
                homeDir.getAcmeSh() + " --create-account-key --server letsencrypt --home " + acmeHome,
                new String[] {},
                5 * 60 * 1000);

        String[] split = cert.getDomain().split(",");
        StringBuffer sb = new StringBuffer();
        Arrays.stream(split).forEach(s -> sb.append(" --domain ").append(s));
        String domain = sb.toString();
        String cmd = null;
        String rs = null;

        Properties properties = new Properties();
        InputStream inputStream = new ByteArrayInputStream((cert.getAux() != null ? cert.getAux() : "").getBytes());
        List<String> envList = new ArrayList<>();
        try {
            properties.load(inputStream);

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                envList.add(key + "=" + value);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        String[] env = envList.toArray(new String[envList.size()]);
        String keylength = "";
        // 在 acme.sh 中，默认情况下使用的是 ECC 密钥对。
        // -k, --keylength <bits>            Specifies the domain key length: 2048, 3072, 4096, 8192 or ec-256, ec-384,
        // ec-521.
        if (cert.getEncryption() != null && cert.getEncryption().intValue() == Encryption.ECC.getId()) {
            keylength = " --keylength ec-256 ";
        } else {
            keylength = " --keylength 2048 ";
        }
        // 对用户输入的 dnsapi 和 domain 做 shell 转义，防止命令注入
        String safeDnsapi = "'" + cert.getDnsapi().replace("'", "'\\''") + "'";
        StringBuilder safeDomain = new StringBuilder();
        for (String s : split) {
            safeDomain.append(" --domain '").append(s.replace("'", "'\\''")).append("'");
        }
        cmd = homeDir.getAcmeSh() + " --issue --force --debug 1 --home " + acmeHome + " --dns " + safeDnsapi
                + safeDomain + keylength + " --server letsencrypt ";
        logger.info(cmd);
        // --staging
        rs = acmeshCmd.execCMD(cmd, env, 5 * 60 * 1000);
        logger.info(rs);

        if (rs.contains("Your cert is in")) {
            // 申请成功, 定位证书
            String firstDomain = cert.getDomain().split(",")[0];
            String certDir = StringUtils.joinWith(File.separator, homeDir.getAcmeHome(), firstDomain);
            if (cert.getEncryption() == Encryption.ECC.getId()) {
                certDir += "_ecc";
            }
            certDir += "/";

            String crtPath = certDir + "fullchain.cer";
            String keyPath = certDir + firstDomain + ".key";

            cert.setCert(FileUtil.readString(crtPath, StandardCharsets.UTF_8));
            cert.setKey(FileUtil.readString(keyPath, StandardCharsets.UTF_8));
            X509Certificate x509Cert = CertUtils.loadCertificate(cert.getCert());
            cert.setNotAfter(x509Cert.getNotAfter().getTime());
            cert.setNotBefore(x509Cert.getNotBefore().getTime());
            cert.setSubject(x509Cert.getSubjectDN().toString());
        } else {
            throw new RuntimeException(rs);
        }
    }

    @Override
    public void http(Cert cert) throws CertificateException, IOException {

        String acmeHome = homeDir.getAcmeHome();
        acmeshCmd.execCMD(
                homeDir.getAcmeSh() + " --create-account-key --server letsencrypt --home " + acmeHome,
                new String[] {},
                5 * 60 * 1000);

        String[] dms = StringUtils.split(cert.getDomain(), ",");
        java.util.List<String> domains = new ArrayList<>();
        Collections.addAll(domains, dms);
        try {
            acmeClient.fetchCertificate(domains);

            String firstDomain = cert.getDomain().split(",")[0];
            String certDir = StringUtils.joinWith(File.separator, homeDir.getAcmeHome(), firstDomain);

            String crtPath = StringUtils.joinWith(File.separator, certDir, "domain-chain.crt");
            String keyPath = StringUtils.joinWith(File.separator, certDir, "domain.key");

            cert.setCert(FileUtil.readString(crtPath, StandardCharsets.UTF_8));
            cert.setKey(FileUtil.readString(keyPath, StandardCharsets.UTF_8));
            X509Certificate x509Cert = CertUtils.loadCertificate(cert.getCert());
            cert.setNotAfter(x509Cert.getNotAfter().getTime());
            cert.setNotBefore(x509Cert.getNotBefore().getTime());
            cert.setSubject(x509Cert.getSubjectDN().toString());

        } catch (Exception ex) {
            logger.error("Failed to get a certificate for domains " + domains, ex);
            throw new RuntimeException(ex);
        }
    }
}
