package com.dyrnq.service;

import com.dyrnq.dso.CaMapper;
import com.dyrnq.dso.CertMapper;
import com.dyrnq.dso.InstMapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class CertService {

    @Inject
    CaMapper caMapper;

    @Inject
    InstMapper instMapper;

    @Inject
    CertMapper certMapper;

}
