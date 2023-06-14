package com.dyrnq.model;

import org.noear.wood.annotation.Column;
import org.noear.wood.annotation.PrimaryKey;
import org.noear.wood.annotation.Table;

@Table("cert")
public class Cert {
    @Column("id")
    @PrimaryKey
    private
    String id;

    @Column("domain")
    private
    String domain;

    @Column("cert")
    private
    String cert;

    @Column("ca_id")
    private
    String caId; //如果是自签名证书 table ca id

    @Column("approach")
    private
    Integer approach;// 方法 0=免费证书 1=手工上传 2=自签名

    @Column("renew")
    private
    Integer renew;// 是否开启自动renew证书

    @Column("supplier")
    private
    Integer supplier;//供应商 0=Let's Encrypt 1=Alicloud 2=TencentCloud

    @Column("encryption")
    private
    Integer encryption;//加密方式 0='RSA' 1='ECC'

    @Column("subject")
    private
    String subject;

    @Column("challenge")
    private
    Integer challenge; // 使用 ACME 标准定义的验证方式来验证您对证书中域名的控制权。80=HTTP-01  53=DNS-01
    //https://letsencrypt.org/zh-cn/docs/challenge-types/

    @Column("private_key")
    private
    String privateKey;

    @Column("not_after")
    private
    Long notAfter;


    @Column("aux")
    private
    String aux; //辅助字段

    @Column("inst_id")
    private
    String instId; //inst table id


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getCaId() {
        return caId;
    }

    public void setCaId(String caId) {
        this.caId = caId;
    }

    public Integer getApproach() {
        return approach;
    }

    public void setApproach(Integer approach) {
        this.approach = approach;
    }

    public Integer getRenew() {
        return renew;
    }

    public void setRenew(Integer renew) {
        this.renew = renew;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Integer getEncryption() {
        return encryption;
    }

    public void setEncryption(Integer encryption) {
        this.encryption = encryption;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getChallenge() {
        return challenge;
    }

    public void setChallenge(Integer challenge) {
        this.challenge = challenge;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Long getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Long notAfter) {
        this.notAfter = notAfter;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }
}
