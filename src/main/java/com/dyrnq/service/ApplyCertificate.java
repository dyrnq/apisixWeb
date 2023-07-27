package com.dyrnq.service;

import com.dyrnq.model.Cert;

import java.io.IOException;
import java.security.cert.CertificateException;

public interface ApplyCertificate {

    /**
     * Challenge by DNS
     *
     * @param cert
     */
    void dns(Cert cert) throws CertificateException, IOException;

    /**
     * Challenge by http file
     *
     * @param cert
     */
    void http(Cert cert) throws CertificateException, IOException;
}
