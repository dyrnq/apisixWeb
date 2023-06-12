package com.dyrnq;

import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

@Component
public class CfgExtractor {
    @Inject("${server.session.state.jwt.name:}")
    private String jwtName;


    public String tokenCookieName() {
        if (StringUtils.isNotBlank(jwtName)) {
            return jwtName;
        }else {
            return CookieName.NAME_TOKEN;
        }
    }
}
