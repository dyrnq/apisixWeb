package com.cym.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class VersionUtils {

    public static String getVersion() throws FileNotFoundException, IOException {

        // 查看jar包里面pom.properties版本号
//        String jarPath = VersionUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
//        jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
//        try {
//            URL url = new URL("jar:file:" + jarPath + "!/META-INF/maven/com.dyrnq/nginxWebUI/pom.properties");
//            InputStream inputStream = url.openStream();
//            Properties properties = new Properties();
//            properties.load(inputStream);
//            String version = properties.getProperty("version");
//            return version;
//        } catch (Exception e) {
//            // 开发过程中查看pom.xml版本号
//            MavenXpp3Reader reader = new MavenXpp3Reader();
//            String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//            if (SystemTool.isWindows() && basePath.startsWith("/")) {
//                basePath = basePath.substring(1);
//            }
//            if (basePath.indexOf("/target/") != -1) {
//                basePath = basePath.substring(0, basePath.indexOf("/target/"));
//            }
//            Model model = reader.read(new FileReader(new File(basePath, "pom.xml")));
//            String version = model.getVersion();
//            return version;
//        }

        return "1.0.0";
    }
}
