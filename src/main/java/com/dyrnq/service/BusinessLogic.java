package com.dyrnq.service;

import com.dyrnq.HomeDir;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.DefaultCredential;
import com.dyrnq.apisix.profile.DefaultProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.apisix.response.Wrap;
import com.dyrnq.dso.UserMapper;
import com.dyrnq.model.User;
import com.dyrnq.service.op.Factory;
import com.dyrnq.service.op.Op;
import com.dyrnq.utils.TarUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.annotation.Db;
import org.noear.wood.ext.Act1;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Component
public class BusinessLogic {
    @Db
    UserMapper userMapper;

    @Inject
    HomeDir homeDir;

    private AdminClient getAdminClient() {
        String url = "192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile(url, "", c);
        AdminClient client = new AdminClient(p);
        return client;
    }

    public User login(String name, String pass) {
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("name", name).andEq("pass", pass);
        };

        List<User> list = userMapper.selectList(condition);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public User findByName(String name) {
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("name", name);
        };

        List<User> list = userMapper.selectList(condition);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public byte[] export(long currentTimeMillis) throws ApisixSDKException, IOException {
        Gson gson = new Gson();//创建gson对象，含有转化的toJson方法
        String rdm = Long.toString(currentTimeMillis);
        String targetFolderPath = homeDir.getTmpAbsolutePath() + File.separator + rdm;
        String targetTarFile = homeDir.getTmpAbsolutePath() + File.separator + rdm + ".tar.gz";
        FileUtils.forceMkdir(new File(targetFolderPath));
        //1，设立一个含有所有队列信息的数组
        String[] clss = {"route", "streamRoute", "upstream", "service", "ssl", "secret", "consumer", "consumerGroup", "globalRule", "pluginConfig", "proto"};
        //2.用增强for循环进行遍历，并根据不同的队列选择不同的list方法。
        AdminClient client = getAdminClient();

        for (String obj : clss) {
            Op op = Factory.create(obj);
            List<?> list = op.list(client);
            //3，根据不同情况进行不同的文件名创建
            String folderName = obj;
            FileUtils.forceMkdir(new File(targetFolderPath + File.separator + folderName));

            for (Object obj1 : list) {
                String id = op.encodeId(obj1);
                File file = new File(targetFolderPath + File.separator + obj + File.separator + id + ".json");//创建file文件地址对象，作为载体
                FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
                String json = gson.toJson(obj1);//创立json字符串形式对象，接收转化后的route （java对象）→（字符串）
                writer.write(json);//执行写入方法。
                writer.close();//关闭写入方法。
            }
            TarUtils.tarGz(targetFolderPath, targetTarFile);
        }
        byte[] bytes = FileUtils.readFileToByteArray(new File(targetTarFile));

        FileUtils.forceDelete(new File(targetFolderPath));
        FileUtils.forceDelete(new File(targetTarFile));

        return bytes;
    }


    public void importData(byte[] b,long currentTimeMillis) throws ApisixSDKException, IOException {
        String rdm = Long.toString(currentTimeMillis);
        String targetFolderPath = homeDir.getTmpAbsolutePath() + File.separator + rdm;
        //String[] clss = {"route", "streamRoute", "upstream", "service", "ssl", "secret", "consumer", "consumerGroup", "globalRule", "pluginConfig", "proto"};
        Class[] clss = new Class[]{Route.class, StreamRoute.class, Upstream.class, Service.class,SSL.class,Secret.class,Consumer.class,ConsumerGroup.class,GlobalRule.class,PluginConfig.class,Proto.class};
        Gson gson = new Gson();
        AdminClient client = getAdminClient();
        String tarGzFilepath = homeDir.getTmpAbsolutePath() + File.separator + rdm+".tar.gz";
        IOUtils.write(b,new FileOutputStream(new File(tarGzFilepath)));
        TarUtils.extractTarGz(tarGzFilepath, targetFolderPath);//执行解压方法

        for (Class obj : clss) {
            String simpleName = obj.getSimpleName();
            simpleName = StringUtils.uncapitalize(simpleName);
            if(obj == SSL.class){
                simpleName = simpleName.toLowerCase();
            }
            File folder = new File(targetFolderPath + File.separator+ simpleName);
            Op op = Factory.create(obj);
            for (File obj1 : folder.listFiles()) {
                FileReader reader = new FileReader(obj1);
                String jsonS = IOUtils.toString(reader);
                String fileName = obj1.getName();
                String id = StringUtils.removeEnd(fileName,".json");
                id = URLDecoder.decode(id,"UTF-8");
                op.putRaw(getAdminClient(), id, jsonS);
                reader.close();
            }
        }
        FileUtils.forceDelete(new File(targetFolderPath));
        FileUtils.forceDelete(new File(tarGzFilepath));

    }

}
