package com.dyrnq.cert.aliyun;

import java.net.URLEncoder;
import java.util.Map;

/**
 * URL 处理工具。
 *
 * @author Alibaba Cloud
 * @date 2019/01/20
 */
public class UrlUtil {
    /**
     * UTF-8 编码。
     */
    private static final String CHARSET_UTF8 = "utf8";

    /**
     * 编码 URL。
     *
     * @param url 要编码的 URL。
     * @return 编码后的 URL。
     */
    public static String urlEncode(String url) {

        if (url != null && !url.isEmpty()) {
            try {
                url = URLEncoder.encode(url, "UTF-8");
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return url;
    }

    /**
     * 规范化请求串。
     *
     * @param params         请求中所有参数的 KV 对。
     * @param shouldEncodeKv 是否需要编码 KV 对中的文本。
     * @return 规范化的请求串。
     */
    public static String canonicalizeQueryString(Map<String, String> params, boolean shouldEncodeKv) {

        StringBuilder canonicalizedQueryString = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {

            if (shouldEncodeKv) {
                canonicalizedQueryString
                        .append(percentEncode(entry.getKey()))
                        .append("=")
                        .append(percentEncode(entry.getValue()))
                        .append("&");
            } else {
                canonicalizedQueryString
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }

        if (canonicalizedQueryString.length() > 1) {
            canonicalizedQueryString.setLength(canonicalizedQueryString.length() - 1);
        }
        return canonicalizedQueryString.toString();
    }

    /**
     * 对原文进行百分号编码。
     *
     * @param text 原文。
     * @return 编码结果。
     */
    public static String percentEncode(String text) {

        try {
            return text == null
                    ? null
                    : URLEncoder.encode(text, CHARSET_UTF8)
                            .replace("+", "%20")
                            .replace("*", "%2A")
                            .replace("%7E", "~");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
