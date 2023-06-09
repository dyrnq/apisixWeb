
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ApisixClientExportTest extends BaseJunit {
    static final String pathString = "C:\\Users\\ash\\Desktop\\export";

    //测试能否成功获取数据并导出
    public String createDir(String folderName, String pathString) throws IOException {
        Path path = Paths.get(pathString, folderName);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        //FileUtils.forceMkdirParent(new java.io.File(pathString+"/"+folderName));
        return pathString + "/" + folderName;
    }

    @Test
    public void test_export() throws ApisixSDKException, IOException {
        Gson gson = new Gson();//创建gson对象，含有转化的toJson方法
        createDir("export","C:\\Users\\ash\\Desktop");
        //1.route队列
        String folderName1 = "route";
        createDir(folderName1, pathString);
        List<Route> routes = client.listRoutes();
        for (Route obj : routes) {
            File file = new File(pathString + "/" + folderName1 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。
        }
        //2.streamRoute队列
        String folderName2 = "streamRoute";
        createDir(folderName2, pathString);
        List<StreamRoute> streamRoute = client.listStreamRoutes();
        for (StreamRoute obj : streamRoute) {
            File file = new File(pathString + "/" + folderName2 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。
        }

        //3.service队列
        String folderName3 = "service";
        createDir(folderName3, pathString);
        List<Service> services = client.listServices();
        for (Service obj : services) {
            File file = new File(pathString + "/" + folderName3 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //4.pluginConfig队列
        String folderName4 = "pluginConfig";
        createDir(folderName4, pathString);
        List<PluginConfig> pluginConfigs = client.listPluginConfigs();
        for (PluginConfig obj : pluginConfigs) {
            File file = new File(pathString + "/" + folderName4 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //5.consumer队列
        String folderName5 = "consumer";
        createDir(folderName5, pathString);
        List<Consumer> consumers = client.listConsumers();
        for (Consumer obj : consumers) {
            File file = new File(pathString + "/" + folderName5 + "/" + obj.getUsername() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //6.consumerGroup队列
        String folderName6 = "consumerGroup";
        createDir(folderName6, pathString);
        List<ConsumerGroup> consumerGroups = client.listConsumerGroups();
        for (ConsumerGroup obj : consumerGroups) {
            File file = new File(pathString + "/" + folderName6 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //7.secret队列
        String folderName7 = "secret";
        createDir(folderName7, pathString);
        List<Secret> secrets = client.listSecrets();
        for (Secret obj : secrets) {
            String encodeId = URLEncoder.encode(obj.getId(), "UTF-8");
            File file = new File(pathString + "/" + folderName7 + "/" + encodeId + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //8.globalRule队列
        String folderName8 = "globalRule";
        createDir(folderName8, pathString);
        List<GlobalRule> globalRules = client.listGlobalRules();
        for (GlobalRule obj : globalRules) {
            File file = new File(pathString + "/" + folderName8 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //9.upstream队列
        String folderName9 = "upstream";
        createDir(folderName9, pathString);
        List<Upstream> upstreams = client.listUpstreams();
        for (Upstream obj : upstreams) {
            File file = new File(pathString + "/" + folderName9 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }

        //10.ssl队列
        String folderName10 = "ssl";
        createDir(folderName10, pathString);
        List<SSL> ssls = client.listSSLs();
        for (SSL obj : ssls) {
            File file = new File(pathString + "/" + folderName10 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //11.proto队列
        String folderName11 = "proto";
        createDir(folderName11, pathString);
        List<Proto> protos = client.listProtos();
        for (Proto obj : protos) {
            File file = new File(pathString + "/" + folderName11 + "/" + obj.getId() + ".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
            String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }


        String sourceDirPath = "C:\\Users\\ash\\Desktop\\export";
        String targetFilePath = "C:\\Users\\ash\\Desktop\\jar.tar.gz";
        tarGz(sourceDirPath,targetFilePath);
    }

    private static void tarGz(String sourceDirPath, String targetFilePath) throws IOException {


        FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        GzipCompressorOutputStream gzipOutputStream = new GzipCompressorOutputStream(bufferedOutputStream);
        TarArchiveOutputStream tarArchiveOutputStream = new TarArchiveOutputStream(gzipOutputStream);

        File sourceDir = new File(sourceDirPath);
        for (File file : sourceDir.listFiles()) {
            addFileToTarGz(tarArchiveOutputStream, "", file);
        }

        tarArchiveOutputStream.close();
        gzipOutputStream.close();
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    private static void addFileToTarGz(TarArchiveOutputStream tarArchiveOutputStream, String base, File file) throws IOException {
        String entryName = base + file.getName();
        TarArchiveEntry tarEntry = new TarArchiveEntry(file, entryName);
        tarArchiveOutputStream.putArchiveEntry(tarEntry);

        if (file.isFile()) {
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
                byte[] buffer = new byte[1024];
                int count;
                while ((count = inputStream.read(buffer)) != -1) {
                    tarArchiveOutputStream.write(buffer, 0, count);
                }
            }
            tarArchiveOutputStream.closeArchiveEntry();
        } else if (file.isDirectory()) {
            tarArchiveOutputStream.closeArchiveEntry();
            for (File childFile : file.listFiles()) {
                String childBase = entryName + "/";
                addFileToTarGz(tarArchiveOutputStream, childBase, childFile);
            }
        }
    }


    //测试能否将外部txt文件以对象形式导入数据库


    public static void extract(String tarGzFilepath, String destDirectory) throws IOException {
        FileInputStream fis = new FileInputStream(tarGzFilepath);
        GzipCompressorInputStream gzis = new GzipCompressorInputStream(fis);
        TarArchiveInputStream tais = new TarArchiveInputStream(gzis);

        TarArchiveEntry entry;
        while ((entry = tais.getNextTarEntry()) != null) {
            File outputFile = new File(destDirectory, entry.getName());
            if (entry.isDirectory()) {
                // 如果是目录，则创建目录
                outputFile.mkdirs();
            } else {
                // 如果是文件，则写入文件内容
                byte[] buffer = new byte[4096];
                int bytesRead;
                FileOutputStream fos = new FileOutputStream(outputFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                while ((bytesRead = tais.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                bos.close();
            }
        }
        tais.close();
    }



    @Test
    public void test_import() throws ApisixSDKException, IOException {
        Gson gson = new Gson();
        extract("C:\\Users\\ash\\Desktop\\jar.tar.gz","C:\\Users\\ash\\Desktop\\export");//执行解压方法
        //1.route队列
        File folder1 = new File(pathString + "/route");
        for (File file1 : folder1.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            Route route = null;
            Type type = new TypeToken<Route>() {
            }.getType();
            route = gson.fromJson(jsonS, type);
            client.putRouteRaw(route.getId(), jsonS);
            reader.close();
        }
        //2.streamRoute
        File folder2 = new File(pathString + "/streamRoute/");
        for (File file1 : folder2.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            StreamRoute streamRoute = null;
            Type type = new TypeToken<StreamRoute>() {
            }.getType();
            streamRoute = gson.fromJson(jsonS, type);
            client.putStreamRouteRaw(streamRoute.getId(), jsonS);
            reader.close();
        }
        //3.service
        File folder3 = new File(pathString + "/service/");
        for (File file1 : folder3.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            Service service = null;
            Type type = new TypeToken<Service>() {
            }.getType();
            service = gson.fromJson(jsonS, type);
            client.putServiceRaw(service.getId(), jsonS);
            reader.close();
        }

        //4.pluginConfig队列
        File folder4 = new File(pathString + "/pluginConfig/");
        for (File file1 : folder4.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            PluginConfig pluginConfig = null;
            Type type = new TypeToken<PluginConfig>() {
            }.getType();
            pluginConfig = gson.fromJson(jsonS, type);
            client.putPluginConfigRaw(pluginConfig.getId(), jsonS);
            reader.close();
        }
        //consumer队列
        File folder5 = new File(pathString + "/consumer/");
        for (File file1 : folder5.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            Consumer consumer = null;
            Type type = new TypeToken<Consumer>() {
            }.getType();
            consumer = gson.fromJson(jsonS, type);
            client.putConsumerRaw(consumer.getUsername(), jsonS);
            reader.close();
        }
        //consumerGroup队列
        File folder6 = new File(pathString + "/consumerGroup/");
        for (File file1 : folder6.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            ConsumerGroup consumerGroup = null;
            Type type = new TypeToken<ConsumerGroup>() {
            }.getType();
            consumerGroup = gson.fromJson(jsonS, type);
            client.putConsumerGroupRaw(consumerGroup.getId(), jsonS);
            reader.close();
        }
        //secret队列
        File folder7 = new File(pathString + "/secret/");
        for (File file1 : folder7.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            Secret secret = null;
            Type type = new TypeToken<Secret>() {
            }.getType();
            secret = gson.fromJson(jsonS, type);
            client.putSecretRaw(secret.getId(), jsonS);
            reader.close();
        }
        //globalRule队列
        File folder8 = new File(pathString + "/globalRule/");
        for (File file1 : folder8.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            GlobalRule globalRule = null;
            Type type = new TypeToken<GlobalRule>() {
            }.getType();
            globalRule = gson.fromJson(jsonS, type);
            client.putGlobalRuleRaw(globalRule.getId(), jsonS);
            reader.close();
        }
        //upstream队列
        File folder9 = new File(pathString + "/upstream/");
        for (File file1 : folder9.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            Upstream upstreame = null;
            Type type = new TypeToken<Upstream>() {
            }.getType();
            upstreame = gson.fromJson(jsonS, type);
            client.putUpstreamRaw(upstreame.getId(), jsonS);
            reader.close();
        }
        //ssl队列
        File folder10 = new File(pathString + "/ssl/");
        for (File file1 : folder10.listFiles()) {
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            SSL ssl = null;
            Type type = new TypeToken<SSL>() {
            }.getType();
            ssl = gson.fromJson(jsonS, type);
            client.putSSLRaw(ssl.getId(), jsonS);
            reader.close();
        }
    }
}
