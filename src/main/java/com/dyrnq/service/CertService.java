package com.dyrnq.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.dyrnq.HomeDir;
import com.dyrnq.cert.acme.AcmeClient;
import com.dyrnq.cert.acme.AcmeshCmd;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.operator.OperatorCreationException;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@Component
public class CertService {
    static Logger logger = LoggerFactory.getLogger(CertService.class);

    @Inject
    CaMapper caMapper;

    @Inject
    InstMapper instMapper;

    @Inject
    CertMapper certMapper;

    @Inject
    HomeDir homeDir;

    @Inject
    AcmeshCmd acmeshCmd;
    @Inject
    AcmeClient acmeClient;


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

        File[] lisFile = new File(homeDir.getAcmeshDir() + File.separator + "dnsapi").listFiles(filter);
        for (File file : lisFile) {
            dnsapiList.add(FileNameUtil.getPrefix(file));
        }
        return dnsapiList;
    }



    public void privateCA(Cert cert) throws CertificateException, IOException, NoSuchAlgorithmException, OperatorCreationException, InvalidAlgorithmParameterException {
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
        if(cert.getEncryption()!=null && cert.getEncryption() == Encryption.ECC.getId()){
            x509Holder = CertUtils.genECC(cert.getSubject(), StringUtils.split(cert.getDomain(), ","), issuerCA, issuerCAKey);
        }else {
            x509Holder = CertUtils.genRSA(cert.getSubject(), StringUtils.split(cert.getDomain(), ","),issuerCA,issuerCAKey);
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


    public void trustCA(Cert cert) throws CertificateException, IOException {

        if (cert.getChallenge() != null && cert.getChallenge().intValue() == Challenge.dns.getId()) {
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
                    envList.add(key + " = " + value);
                }

            } catch (IOException e) {
                //
            }
            String home = " --home " + homeDir.getAcmeHome();
            acmeshCmd.execCMD(homeDir.getAcmeSh() + " --create-account-key --server letsencrypt" + home, new String[]{}, 5 * 60 * 1000);


            String[] env = envList.toArray(new String[envList.size()]);
            String keylength = "";
            if (cert.getEncryption() != null && cert.getEncryption().intValue() == Encryption.ECC.getId()) {
                keylength = " --ecc --keylength ec-256 ";
            }
            cmd = homeDir.getAcmeSh() + " --issue --force " + home + " --dns " + cert.getDnsapi() + domain + keylength + " --server letsencrypt --staging";
            rs = acmeshCmd.execCMD(cmd, env, 5 * 60 * 1000);
            logger.info(rs);

            if (rs.contains("Your cert is in")) {
                // 申请成功, 定位证书
                String firstDomain = cert.getDomain().split(",")[0];
                String certDir = homeDir.getAcmeHome() +File.separator+ firstDomain;
                if ( cert.getEncryption() == Encryption.ECC.getId()) {
                    certDir += "_ecc";
                }
                certDir += "/";

                String crtPath = certDir + "fullchain.cer";
                String keyPath = certDir + firstDomain+".key";

                cert.setCert(FileUtil.readString(crtPath,"UTF-8"));
                cert.setKey(FileUtil.readString(keyPath,"UTF-8"));
                X509Certificate x509Cert = CertUtils.loadCertificate(cert.getCert());
                cert.setNotAfter(x509Cert.getNotAfter().getTime());
                cert.setNotBefore(x509Cert.getNotBefore().getTime());
                cert.setSubject(x509Cert.getSubjectDN().toString());
            }else{
                throw new RuntimeException("error");
            }
        } else if (cert.getChallenge() != null && cert.getChallenge().intValue() == Challenge.http.getId()) {
            String[] dms = StringUtils.split(cert.getDomain(), ",");
            java.util.List<String> domains = new ArrayList<>();
            Collections.addAll(domains, dms);
            try {
                acmeClient.fetchCertificate(domains);

                String firstDomain = cert.getDomain().split(",")[0];
                String certDir = homeDir.getAcmeHome() +File.separator+ firstDomain;


                String crtPath = certDir +File.separator+ "domain-chain.crt";
                String keyPath = certDir +File.separator+ "domain.key";

                cert.setCert(FileUtil.readString(crtPath,"UTF-8"));
                cert.setKey(FileUtil.readString(keyPath,"UTF-8"));
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



    public void issue(Cert cert) throws InvalidNameException, CertificateException, IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, OperatorCreationException {
        if (cert.getApproach() == Approach.manual.getId()) {
            this.manual(cert);
        } else if (cert.getApproach() == Approach.trustCA.getId()) {
            this.trustCA(cert);
        } else if (cert.getApproach() == Approach.privateCA.getId()) {
            this.privateCA(cert);
        }
    }

    public void renew(Cert cert) throws InvalidNameException, CertificateException, IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, OperatorCreationException {
        if (cert.getApproach() == Approach.manual.getId()) {
            this.manual(cert);
        } else if (cert.getApproach() == Approach.trustCA.getId()) {
            this.trustCA(cert);
        } else if (cert.getApproach() == Approach.privateCA.getId()) {
            this.privateCA(cert);
        }
    }

}
