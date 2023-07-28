package com.dyrnq.cert.aliyun;

import org.apache.commons.lang3.StringUtils;

public class DomainUtils {

    /**
     * 获取域名的TLD
     *
     * @param line
     * @return
     */
    public static String getTLD(String line) {
        int dotIndex = line.lastIndexOf('.');
        return (dotIndex == -1) ? line : line.substring(dotIndex + 1);
    }

    /**
     * 获取这个子域名的域名
     *
     * @param domain
     * @return
     */
    public static String base(String domain) {
        String[] domainx = StringUtils.split(domain, ".");
        String baseDomain = domainx[domainx.length - 2] + "." + domainx[domainx.length - 1];
        return baseDomain;
    }

}
