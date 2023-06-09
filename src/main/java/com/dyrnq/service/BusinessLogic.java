package com.dyrnq.service;

import com.dyrnq.HomeDir;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.DefaultCredential;
import com.dyrnq.apisix.profile.DefaultProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.dso.UserMapper;
import com.dyrnq.model.User;
import com.dyrnq.utils.TarUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.annotation.Db;
import org.noear.wood.ext.Act1;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLEncoder;

import java.util.List;

@Component
public class BusinessLogic  {
    @Db
    UserMapper userMapper;

    @Inject
    HomeDir homeDir;

    private AdminClient getAdminClient(){
        String url ="192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile (url ,"", c);
        AdminClient client =new AdminClient(p);
        return client;
    }

    public User login(String name, String pass){
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("name",name).andEq("pass",pass);
        };

        List<User> list = userMapper.selectList(condition);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public User findByName(String name){
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("name",name);
        };

        List<User> list = userMapper.selectList(condition);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }


    public byte[] export(long currentTimeMillis) throws ApisixSDKException, IOException {
        Gson gson = new Gson();//创建gson对象，含有转化的toJson方法
        String rdm = Long.toString(currentTimeMillis);
        String pathString = homeDir.getTmpAbsolutePath()+File.separator+rdm;
        String targetFilePath = homeDir.getTmpAbsolutePath()+File.separator+ rdm+".tar.gz";
        FileUtils.forceMkdir(new File(pathString));
        //1，设立一个含有所有队列信息的数组
        String [] clss = {"route","streamRoute","upstream","service","ssl","secret","consumer","consumerGroup","globalRule","pluginConfig","proto"};
        List<?> list = null;
        //2.用增强for循环进行遍历，并根据不同的队列选择不同的list方法。
        AdminClient client = getAdminClient();

        for(String obj: clss) {
            switch (obj) {
                case "route":
                    list = client.listRoutes();
                    break;

                case "StreamRoute":
                    list = client.listStreamRoutes();
                    break;

                case "upstream":
                    list = client.listUpstreams();
                    break;

                case "service":
                    list = client.listServices();
                    break;

                case "ssl":
                    list = client.listSSLs();
                    break;
                case "secret":
                    list = client.listSecrets();
                    break;
                case "consumer":
                    list = client.listConsumers();
                    break;
                case "consumerGroup":
                    list = client.listConsumerGroups();
                    break;
                case "globalRule":
                    list = client.listGlobalRules();
                    break;
                case "pluginConfig":
                    list = client.listPluginConfigs();
                    break;
                case "proto":
                    list = client.listProtos();
                    break;
                default:
                    break;
            }
            //3，根据不同情况进行不同的文件名创建
            String folderName = obj;
            FileUtils.forceMkdir(new File(pathString+File.separator+folderName));

            for (Object obj1 : list) {
                String id ="";
                if (obj1 instanceof Route) { id = ((Route) obj1).getId();}
                else if( obj1 instanceof Consumer) {id =((Consumer) obj1).getUsername();}
                else if( obj1 instanceof StreamRoute) {id = ((StreamRoute) obj1).getId();}
                else if( obj1 instanceof Upstream) {id = ((Upstream) obj1).getId();}
                else if( obj1 instanceof Service) {id = ((Service) obj1).getId();}
                else if( obj1 instanceof ConsumerGroup) {id = ((ConsumerGroup) obj1).getId();}
                else if( obj1 instanceof GlobalRule) {id = ((GlobalRule) obj1).getId();}
                else if( obj1 instanceof PluginConfig) {id = ((PluginConfig) obj1).getId();}
                else if( obj1 instanceof Proto) {id = ((Proto) obj1).getId();}
                else if( obj1 instanceof SSL) {id = ((SSL) obj1).getId();}
                else if( obj1 instanceof Secret) {id = URLEncoder.encode(((Secret) obj1).getId(),"UTF-8");}

                File file = new File(pathString + "/" + obj + "/" + id + ".json");//创建file文件地址对象，作为载体
                FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
                String json = gson.toJson(obj);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
                writer.write(json);//执行写入方法。
                writer.close();//关闭写入方法。
            }
            TarUtils.tarGz(pathString,targetFilePath);


        }
        byte[] bytes = FileUtils.readFileToByteArray(new File(targetFilePath));

        FileUtils.forceDelete(new File(pathString));
        FileUtils.forceDelete(new File(targetFilePath));

        return bytes;
    }


    public void importData() throws ApisixSDKException, IOException {
        String pathString = "";
        Gson gson = new Gson();
        AdminClient client = getAdminClient();
        TarUtils.extractTarGz("C:\\Users\\ash\\Desktop\\jar.tar.gz",pathString);//执行解压方法
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
