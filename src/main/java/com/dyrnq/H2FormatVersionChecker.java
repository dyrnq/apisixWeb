package com.dyrnq;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class H2FormatVersionChecker {
    public static boolean isVer2(String filePath) {
        return checkVersion(filePath, 2);
    }

    public static boolean isVer3(String filePath) {
        return checkVersion(filePath, 3);
    }

    private static boolean checkVersion(String filePath, int ver) {
        InputStream fis = null;
        byte[] header = new byte[512];
        try {
            fis = new FileInputStream(filePath);
            int bytesRead = fis.read(header);
            if (bytesRead < 0) {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fis);
        }

        String fileHeader = new String(header, java.nio.charset.StandardCharsets.UTF_8);
        return StringUtils.contains(fileHeader, "format:" + ver);
    }
}
