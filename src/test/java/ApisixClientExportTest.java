import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.response.Multi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApisixClientExportTest extends BaseJunit{
    //测试能否成功获取数据并导出
    @Test
    public void test_export() throws ApisixSDKException, IOException {
        //1.route队列
        String folderName1 = "route";
        String pathString1 = "C:/Users/ash/Desktop";
        Path path1 = Paths.get(pathString1, folderName1);
        if (!Files.exists(path1)) {
            Files.createDirectory(path1);
        }
        List<Route>routes = client.listRoutes();
        Gson gson = new Gson();//创建gson对象，含有转化的toJson方法
        for (Route route1 : routes) {
        File file = new File("C:/Users/ash/Desktop/route/"+route1.getId()+".json");//创建file文件地址对象，作为载体
        FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

        String json = gson.toJson(route1);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
        writer.write(json);//执行写入方法。
        writer.close();//关闭写入方法。


        }
        //2.streamRoute队列
        String folderName2 = "streamRoute";
        String pathString2 = "C:/Users/ash/Desktop";
        Path path2 = Paths.get(pathString2, folderName2);
        if (!Files.exists(path2)) {
            Files.createDirectory(path2);
        }
        List<StreamRoute>streamRoutes = client.listStreamRoutes();
        for (StreamRoute streamRoute1 : streamRoutes) {
            File file = new File("C:/Users/ash/Desktop/streamRoute/"+streamRoute1.getId()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(streamRoute1);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }

        //3.service队列
        String folderName3 = "service";
        String pathString3 = "C:/Users/ash/Desktop";
        Path path3 = Paths.get(pathString3, folderName3);
        if (!Files.exists(path3)) {
            Files.createDirectory(path3);
        }
        List<Service>services = client.listServices();
        for (Service service1 : services) {
            File file = new File("C:/Users/ash/Desktop/service/"+service1.getId()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(service1);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //4.pluginConfig队列
        String folderName4 = "pluginConfig";
        String pathString4 = "C:/Users/ash/Desktop";
        Path path4 = Paths.get(pathString4, folderName4);
        if (!Files.exists(path4)) {
            Files.createDirectory(path4);
        }
        List<PluginConfig> pluginConfigs = client.listPluginConfigs();
        for (PluginConfig pluginConfig : pluginConfigs) {
            File file = new File("C:/Users/ash/Desktop/pluginConfig/"+pluginConfig.getId()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(pluginConfig);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //5.consumer队列
        String folderName5 = "consumer";
        String pathString5 = "C:/Users/ash/Desktop";
        Path path5 = Paths.get(pathString5, folderName5);
        if (!Files.exists(path5)) {
            Files.createDirectory(path5);
        }
        List<Consumer> consumers = client.listConsumers();
        for (Consumer consumer : consumers) {
            File file = new File("C:/Users/ash/Desktop/consumer/"+consumer.getUsername()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(consumer);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //6.consumerGroup队列
        String folderName6 = "consumerGroup";
        String pathString6= "C:/Users/ash/Desktop";
        Path path6 = Paths.get(pathString6, folderName6);
        if (!Files.exists(path6)) {
            Files.createDirectory(path6);
        }
        List<ConsumerGroup> consumerGroups = client.listConsumerGroups();
        for (ConsumerGroup consumerGroup : consumerGroups) {
            File file = new File("C:/Users/ash/Desktop/consumerGroup/"+consumerGroup.getId()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(consumerGroup);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //7.secret队列
        String folderName7 = "secret";
        String pathString7= "C:/Users/ash/Desktop";
        Path path7 = Paths.get(pathString7, folderName7);
        if (!Files.exists(path7)) {
            Files.createDirectory(path7);
        }
        List<Secret> secrets = client.listSecrets();
        for (Secret secret : secrets) {
            String fileN = URLEncoder.encode(secret.getId(),"UTF-8");
            File file = new File("C:/Users/ash/Desktop/secret/"+fileN+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(secret);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //8.globalRule队列
        String folderName8 = "globalRule";
        String pathString8= "C:/Users/ash/Desktop";
        Path path8 = Paths.get(pathString8, folderName8);
        if (!Files.exists(path8)) {
            Files.createDirectory(path8);
        }
        List<GlobalRule> globalRules = client.listGlobalRules();
        for (GlobalRule globalRule : globalRules) {
            File file = new File("C:/Users/ash/Desktop/globalRule/"+globalRule.getId()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(globalRule);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }
        //9.upstream队列
        String folderName9 = "upstream";
        String pathString9= "C:/Users/ash/Desktop";
        Path path9 = Paths.get(pathString9, folderName9);
        if (!Files.exists(path9)) {
            Files.createDirectory(path9);
        }
        List<Upstream> upstreams = client.listUpstreams();
        for (Upstream upstream : upstreams) {
            File file = new File("C:/Users/ash/Desktop/upstream/"+upstream.getId()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(upstream);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }

        //10.ssl队列
        String folderName10 = "ssl";
        String pathString10= "C:/Users/ash/Desktop";
        Path path10 = Paths.get(pathString10, folderName10);
        if (!Files.exists(path10)) {
            Files.createDirectory(path10);
        }
        List<SSL> ssls = client.listSSLs();
        for (SSL ssl : ssls) {
            File file = new File("C:/Users/ash/Desktop/ssl/"+ssl.getId()+".json");//创建file文件地址对象，作为载体
            FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。

            String json = gson.toJson(ssl);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
            writer.write(json);//执行写入方法。
            writer.close();//关闭写入方法。


        }

    }

    //测试能否将外部txt文件以对象形式导入数据库
    @Test
    public void test_import() throws ApisixSDKException,IOException {
        //1.route队列
        File folder1 = new File("C:/Users/ash/Desktop/route/");
        File[] files1 = folder1.listFiles();
        Gson gson = new Gson();
        for (File file1:files1){
            FileReader reader = new FileReader(file1);
            String jsonS = IOUtils.toString(reader);
            Route route = null;
            Type type = new TypeToken<Route>() {}.getType();
            route = gson.fromJson(jsonS, type);
            client.putRouteRaw(route.getId(), jsonS);
            reader.close();
        }
        //2.streamRoute
        File folder2 = new File("C:/Users/ash/Desktop/streamRoute/");
        File[] files2 = folder2.listFiles();
        for (File file2:files2){
            FileReader reader = new FileReader(file2);
            String jsonS = IOUtils.toString(reader);
            StreamRoute streamRoute = null;
            Type type = new TypeToken<StreamRoute>() {}.getType();
            streamRoute = gson.fromJson(jsonS, type);
            client.putStreamRouteRaw(streamRoute.getId(), jsonS);
            reader.close();
        }
        //3.service
        File folder3 = new File("C:/Users/ash/Desktop/service/");
        File[] files3 = folder3.listFiles();
        for (File file3:files3){
            FileReader reader = new FileReader(file3);
            String jsonS = IOUtils.toString(reader);
            Service service = null;
            Type type = new TypeToken<Service>() {}.getType();
            service = gson.fromJson(jsonS, type);
            client.putServiceRaw(service.getId(), jsonS);
            reader.close();
        }

        //4.pluginConfig队列
        File folder4 = new File("C:/Users/ash/Desktop/pluginConfig/");
        File[] files4 = folder4.listFiles();
        for (File file4:files4){
            FileReader reader = new FileReader(file4);
            String jsonS = IOUtils.toString(reader);
            PluginConfig pluginConfig = null;
            Type type = new TypeToken<PluginConfig>() {}.getType();
            pluginConfig = gson.fromJson(jsonS, type);
            client.putPluginConfigRaw(pluginConfig.getId(), jsonS);
            reader.close();
        }
        //consumer队列
        File folder5 = new File("C:/Users/ash/Desktop/consumer/");
        File[] files5 = folder5.listFiles();
        for (File file5:files5){
            FileReader reader = new FileReader(file5);
            String jsonS = IOUtils.toString(reader);
            Consumer consumer = null;
            Type type = new TypeToken<Consumer>() {}.getType();
            consumer = gson.fromJson(jsonS, type);
            client.putConsumerRaw(consumer.getUsername(), jsonS);
            reader.close();
        }
        //consumerGroup队列
        File folder6 = new File("C:/Users/ash/Desktop/consumerGroup/");
        File[] files6 = folder6.listFiles();
        for (File file6:files6){
            FileReader reader = new FileReader(file6);
            String jsonS = IOUtils.toString(reader);
            ConsumerGroup consumerGroup = null;
            Type type = new TypeToken<ConsumerGroup>() {}.getType();
            consumerGroup = gson.fromJson(jsonS, type);
            client.putConsumerGroupRaw(consumerGroup.getId(), jsonS);
            reader.close();
        }
        //secret队列
        File folder7 = new File("C:/Users/ash/Desktop/secret/");
        File[] files7 = folder7.listFiles();
        for (File file7:files7){
            FileReader reader = new FileReader(file7);
            String jsonS = IOUtils.toString(reader);
            Secret secret = null;
            Type type = new TypeToken<Secret>() {}.getType();
            secret= gson.fromJson(jsonS, type);
            client.putSecretRaw(secret.getId(), jsonS);
            reader.close();
        }
        //globalRule队列
        File folder8 = new File("C:/Users/ash/Desktop/globalRule/");
        File[] files8 = folder8.listFiles();
        for (File file8:files8){
            FileReader reader = new FileReader(file8);
            String jsonS = IOUtils.toString(reader);
            GlobalRule globalRule = null;
            Type type = new TypeToken<GlobalRule>() {}.getType();
            globalRule = gson.fromJson(jsonS, type);
            client.putGlobalRuleRaw(globalRule.getId(), jsonS);
            reader.close();
        }
        //upstream队列
        File folder9 = new File("C:/Users/ash/Desktop/upstream/");
        File[] files9 = folder9.listFiles();
        for (File file9:files9){
            FileReader reader = new FileReader(file9);
            String jsonS = IOUtils.toString(reader);
            Upstream upstreame = null;
            Type type = new TypeToken<Upstream>() {}.getType();
            upstreame = gson.fromJson(jsonS, type);
            client.putUpstreamRaw(upstreame.getId(), jsonS);
            reader.close();
        }
        //ssl队列
        File folder10 = new File("C:/Users/ash/Desktop/ssl/");
        File[] files10 = folder10.listFiles();
        for (File file10:files10){
            FileReader reader = new FileReader(file10);
            String jsonS = IOUtils.toString(reader);
            SSL ssl = null;
            Type type = new TypeToken<SSL>() {}.getType();
            ssl = gson.fromJson(jsonS, type);
            client.putSSLRaw(ssl.getId(), jsonS);
            reader.close();
        }
    }
}
