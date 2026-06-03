package com.dyrnq.service;

import cn.hutool.core.io.file.FileNameUtil;
import com.dyrnq.HomeDir;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.SSL;
import com.dyrnq.dso.CaMapper;
import com.dyrnq.dso.CertMapper;
import com.dyrnq.dso.InstMapper;
import com.dyrnq.model.Ca;
import com.dyrnq.model.Cert;
import com.dyrnq.utils.CertUtils;
import com.dyrnq.utils.X509Holder;
import enumeration.Approach;
import enumeration.Challenge;
import enumeration.Encryption;
import enumeration.Supplier;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.naming.InvalidNameException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.operator.OperatorCreationException;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CertService {
    static Logger logger = LoggerFactory.getLogger(CertService.class);

    @Inject
    CaMapper caMapper;

    @Inject
    InstMapper instMapper;

    @Inject
    CertMapper certMapper;

    @Inject(value = "acmeImpl")
    ApplyCertificate acme;

    @Inject(value = "tencentImpl")
    ApplyCertificate tencent;

    @Inject(value = "aliyunImpl")
    ApplyCertificate aliyun;

    @Inject
    BusinessLogic businessLogic;

    @Inject
    HomeDir homeDir;

    protected AdminClient getAdminClient() throws ApisixSDKException {
        return businessLogic.getAdminClient();
    }

    /**
     * 获取acmesh支持的所有dnsapi
     *
     * @return
     */
    public List<String> getAcmeshDnsapi() {
        List<String> dnsapiList = new ArrayList<>();
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return StringUtils.endsWith(name, ".sh");
            }
        };

        File[] listFile = new File(homeDir.getAcmeshDir() + File.separator + "dnsapi").listFiles(filter);

        if (listFile != null) {
            for (File file : listFile) {
                dnsapiList.add(FileNameUtil.getPrefix(file));
            }
        }
        return dnsapiList;
    }

    public void privateCA(Cert cert)
            throws CertificateException, IOException, NoSuchAlgorithmException, OperatorCreationException,
                    InvalidAlgorithmParameterException {
        if (StringUtils.isBlank(cert.getSubject())) {
            cert.setSubject("CN=" + StringUtils.split(cert.getDomain(), ",")[0]);
        }
        X509Holder x509Holder = null;
        X509Certificate issuerCA = null;
        PrivateKey issuerCAKey = null;
        if (StringUtils.isNoneBlank(cert.getCaId())) {
            Ca ca = caMapper.selectById(cert.getCaId());
            issuerCA = CertUtils.loadCertificate(ca.getCert());
            issuerCAKey = CertUtils.load(ca.getKey());
        }
        if (cert.getEncryption() != null && cert.getEncryption() == Encryption.ECC.getId()) {
            x509Holder = CertUtils.genECC(
                    cert.getSubject(), StringUtils.split(cert.getDomain(), ","), issuerCA, issuerCAKey);
        } else {
            x509Holder = CertUtils.genRSA(
                    cert.getSubject(), StringUtils.split(cert.getDomain(), ","), issuerCA, issuerCAKey);
        }

        cert.setCert(x509Holder.getCert());
        cert.setKey(x509Holder.getKey());
        X509Certificate x509Cert = x509Holder.getCertificate();
        cert.setNotAfter(x509Cert.getNotAfter().getTime());
        cert.setNotBefore(x509Cert.getNotBefore().getTime());
    }

    public void manual(Cert cert) throws CertificateException, InvalidNameException, IOException {
        if (cert.getCertFile() != null) {
            cert.setCert(IOUtils.toString(cert.getCertFile().getContent(), StandardCharsets.UTF_8));
        }
        if (cert.getKeyFile() != null) {
            cert.setKey(IOUtils.toString(cert.getKeyFile().getContent(), StandardCharsets.UTF_8));
        }
        X509Certificate x509Cert = CertUtils.loadCertificate(cert.getCert());
        cert.setSubject(x509Cert.getSubjectDN().toString());
        cert.setNotAfter(x509Cert.getNotAfter().getTime());
        cert.setNotBefore(x509Cert.getNotBefore().getTime());
        String[] sniArray = CertUtils.extractSNI(x509Cert);
        cert.setDomain(StringUtils.join(sniArray, ","));
    }

    private ApplyCertificate getApplyCertificate(int id) {
        if (id == Supplier.acme.getId()) {
            return acme;
        } else if (id == Supplier.aliyun.getId()) {
            return aliyun;
        } else if (id == Supplier.tencent.getId()) {
            return tencent;
        }
        return acme;
    }

    public void trustCA(Cert cert) throws CertificateException, IOException {
        if (cert.getChallenge() != null && cert.getChallenge().intValue() == Challenge.dns.getId()) {
            getApplyCertificate(cert.getSupplier().intValue()).dns(cert);
        } else if (cert.getChallenge() != null && cert.getChallenge().intValue() == Challenge.http.getId()) {
            getApplyCertificate(cert.getSupplier().intValue()).http(cert);
        }
    }

    public void issue(Cert cert)
            throws InvalidNameException, CertificateException, IOException, InvalidAlgorithmParameterException,
                    NoSuchAlgorithmException, OperatorCreationException {
        if (cert.getApproach() == Approach.trustCA.getId()) {
            this.trustCA(cert);
        } else if (cert.getApproach() == Approach.privateCA.getId()) {
            this.privateCA(cert);
        }
        upsertSSL(cert);
    }

    public void renew(Cert cert)
            throws InvalidNameException, CertificateException, IOException, InvalidAlgorithmParameterException,
                    NoSuchAlgorithmException, OperatorCreationException {
        if (cert.getApproach() == Approach.trustCA.getId()) {
            this.trustCA(cert);
        } else if (cert.getApproach() == Approach.privateCA.getId()) {
            this.privateCA(cert);
        }
        upsertSSL(cert);
    }

    /**
     * 插入或者更新apisix的SSL存储
     */
    protected void upsertSSL(Cert cert) {
        String[] dms = StringUtils.split(cert.getDomain(), ",");
        java.util.List<String> domains = new ArrayList<>();
        Collections.addAll(domains, dms);
        Collections.sort(domains);
        try {
            String id = sslID(cert);
            if (StringUtils.isNotBlank(id)) {
                SSL ssl = new SSL();
                ssl.setId(id);
                ssl.setType("server");
                ssl.setSnis(domains);
                ssl.setStatus(1);
                ssl.setCert(cert.getCert());
                ssl.setKey(cert.getKey());
                getAdminClient().putSSL(id, ssl);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ApisixSDKException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据domain计算id
     *
     * @param cert
     * @return
     * @throws NoSuchAlgorithmException
     */
    protected String sslID(Cert cert) throws NoSuchAlgorithmException {
        String[] dms = StringUtils.split(cert.getDomain(), ",");
        java.util.List<String> domains = new ArrayList<>();
        Collections.addAll(domains, dms);
        Collections.sort(domains);
        String arrayString = String.join(",", domains);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(arrayString.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
