package com.dyrnq.service;

import cn.hutool.core.codec.Base64;
import com.dyrnq.CookieName;
import com.dyrnq.HomeDir;
import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.DefaultCredential;
import com.dyrnq.apisix.profile.DefaultProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.dso.InstMapper;
import com.dyrnq.dso.UserMapper;
import com.dyrnq.model.Inst;
import com.dyrnq.model.User;
import com.dyrnq.service.op.Factory;
import com.dyrnq.service.op.Op;
import com.dyrnq.utils.BCryptPasswordEncoder;
import com.dyrnq.utils.TarUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.Context;
import org.noear.solon.i18n.I18nUtil;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.annotation.Db;
import org.noear.wood.ext.Act1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.List;

@Component
public class BusinessLogic {
    static Logger logger = LoggerFactory.getLogger(BusinessLogic.class);
    @Db
    UserMapper userMapper;

    @Db
    InstMapper instMapper;
    @Inject
    HomeDir homeDir;

    public AdminClient getAdminClient() throws ApisixSDKException {
        String instId = Context.current().cookieOrDefault(CookieName.NAME_INSTID, "1");
        return getAdminClient(instId);
    }

    public AdminClient getAdminClient(String instId) throws ApisixSDKException {
        AdminClient client = null;
        try {
            Inst inst = instMapper.selectById(instId);
            if (inst != null) {
                String url = inst.getUrl();
                Credential c = new DefaultCredential(inst.getApiKey());
                Profile p = DefaultProfile.getProfile(url, "", c);
                client = new AdminClient(p);
            }
        } catch (Exception e) {
            throw new ApisixSDKException(e.getMessage());
        }
        return client;
    }

    /**
     * 使用base64传入用户名和密码进行登录,传入的参数base64Name和base64Pass都被base64过两次，因此这里也要解开两次
     *
     * @param base64Name
     * @param base64Pass
     * @return
     */
    public User login(String base64Name, String base64Pass) {
        String name = Base64.decodeStr(Base64.decodeStr(base64Name));
        String pass = Base64.decodeStr(Base64.decodeStr(base64Pass));
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("name", name);
        };

        List<User> list = userMapper.selectList(condition);
        if (list != null && list.size() > 0) {
            User user = list.get(0);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            if (encoder.matches(pass, user.getPass())) {
                return user;
            } else {
                throw new RuntimeException(I18nUtil.getMessage("loginStr.backError2"));
            }
        } else {
            throw new RuntimeException(I18nUtil.getMessage("loginStr.backError5"));
        }

        //return null;
    }

    /**
     * 为某个用户更改密码,传入的密码参数(base64Pass)base64过两次，因此这里也要解开两次
     *
     * @param id
     * @param base64Pass
     */
    public void changePass(String id, String base64Pass) {
        String pass = Base64.decodeStr(Base64.decodeStr(base64Pass));
        User user = new User();
        user.setId(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPass(encoder.encode(pass));
        userMapper.updateById(user, true);
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

    public void drop(String instId) throws ApisixSDKException {
        Class[] clss = new Class[]{Route.class, Service.class, Upstream.class, StreamRoute.class, SSL.class, Secret.class, ConsumerGroup.class, Consumer.class, GlobalRule.class, PluginConfig.class, Proto.class};
        AdminClient client = getAdminClient(instId);
        //2.用增强for循环进行遍历，并根据不同的队列选择不同的list方法。
        for (Class obj : clss) {
            Op op = Factory.create(obj);
            op.drop(client);
        }

    }


    public byte[] export(String instId, long currentTimeMillis, String format) throws ApisixSDKException, IOException {
        //创建gson对象，含有转化的toJson方法
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return "createTime".equals(f.getName()) || "updateTime".equals(f.getName()); // 如果是特殊字段，则排除
// 其他字段都保留
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();


        JsonMapper jsonMapper = new JsonMapper();
        YAMLMapper yamlMapper = new YAMLMapper();
        // 配置 ObjectMapper 忽略值为 null 的字段
        //jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String rdm = Long.toString(currentTimeMillis);
        String targetFolderPath = homeDir.getTmpAbsolutePath() + File.separator + rdm;
        String targetTarFile = homeDir.getTmpAbsolutePath() + File.separator + rdm + ".tar.gz";
        FileUtils.forceMkdir(new File(targetFolderPath));
        //1，设立一个含有所有队列信息的数组
        Class[] clss = new Class[]{Route.class, StreamRoute.class, Upstream.class, Service.class, SSL.class, Secret.class, Consumer.class, ConsumerGroup.class, GlobalRule.class, PluginConfig.class, Proto.class};


        AdminClient client = getAdminClient(instId);
        //2.用增强for循环进行遍历，并根据不同的队列选择不同的list方法。
        for (Class obj : clss) {
            Op op = Factory.create(obj);
            List<?> list = op.list(client);
            //3，根据不同情况进行不同的文件名创建
            String simpleName = obj.getSimpleName();
            simpleName = StringUtils.uncapitalize(simpleName);
            if (obj == SSL.class) {
                simpleName = simpleName.toLowerCase();
            }
            FileUtils.forceMkdir(new File(targetFolderPath + File.separator + simpleName));

            String fileEnd = StringUtils.equalsIgnoreCase("yaml", format) ? "yaml" : "json";
            for (Object item : list) {
                String id = op.encodeId(item);
                File file = new File(targetFolderPath + File.separator + simpleName + File.separator + id + "." + fileEnd);//创建file文件地址对象，作为载体
                FileWriter writer = new FileWriter(file);//创建writer对象，含有写入方法。
                String content = null; //创立json字符串形式对象，接收转化后的route （java对象）→（字符串）

                if (StringUtils.equalsIgnoreCase("yaml", format)) {
                    // 将JSON字符串转换为JsonNode对象

                    JsonNode jsonNode = jsonMapper.readTree(gson.toJson(item));

                    // 将JsonNode对象转换为YAML字符串
                    content = yamlMapper.writeValueAsString(jsonNode);

                } else {
                    content = gson.toJson(item);
                }

                writer.write(content);//执行写入方法。
                writer.close();//关闭写入方法。
            }

            TarUtils.tarGz(targetFolderPath, targetTarFile);


        }
        byte[] bytes = FileUtils.readFileToByteArray(new File(targetTarFile));

//        FileUtils.forceDelete(new File(targetFolderPath));
//        FileUtils.forceDelete(new File(targetTarFile));

        return bytes;
    }


    public void importData(String instId, byte[] b, long currentTimeMillis) throws ApisixSDKException, IOException {
        String rdm = Long.toString(currentTimeMillis);
        String targetFolderPath = homeDir.getTmpAbsolutePath() + File.separator + rdm;

        JsonMapper jsonMapper = new JsonMapper();
        YAMLMapper yamlMapper = new YAMLMapper();

        Class[] clss = new Class[]{PluginConfig.class, Upstream.class, Service.class, Route.class, StreamRoute.class, SSL.class, Secret.class, Consumer.class, ConsumerGroup.class, GlobalRule.class, Proto.class};

        AdminClient client = getAdminClient(instId);
        String tarGzFilepath = homeDir.getTmpAbsolutePath() + File.separator + rdm + ".tar.gz";
        IOUtils.write(b, new FileOutputStream(new File(tarGzFilepath)));
        TarUtils.extractTarGz(tarGzFilepath, targetFolderPath);//执行解压方法

        for (Class obj : clss) {
            String simpleName = obj.getSimpleName();
            simpleName = StringUtils.uncapitalize(simpleName);
            if (obj == SSL.class) {
                simpleName = simpleName.toLowerCase();
            }
            File folder = new File(targetFolderPath + File.separator + simpleName);
            Op op = Factory.create(obj);
            for (File item : folder.listFiles()) {
                FileReader reader = new FileReader(item);
                String content = IOUtils.toString(reader);
                String fileName = item.getName();

                String id = null;
                if (StringUtils.endsWithIgnoreCase(fileName, ".json")) {
                    id = StringUtils.removeEnd(fileName, ".json");
                } else if (StringUtils.endsWithIgnoreCase(fileName, ".yaml")) {
                    id = StringUtils.removeEnd(fileName, ".yaml");
                } else if (StringUtils.endsWithIgnoreCase(fileName, ".yml")) {
                    id = StringUtils.removeEnd(fileName, ".yml");
                }

                id = URLDecoder.decode(id, "UTF-8");

                JsonNode jsonNode = null;

                //将yaml转化为json
                if (!StringUtils.endsWithIgnoreCase(fileName, ".json")) {
                    jsonNode = yamlMapper.readTree(content);
                } else {
                    jsonNode = jsonMapper.readTree(content);
                }

                ObjectNode objectNode = (ObjectNode) jsonNode;

                for (String removeKey : new String[]{"create_time", "update_time"})
                    if (objectNode.has(removeKey)) {
                        objectNode.remove(removeKey);
                    }
                content = jsonMapper.writeValueAsString(jsonNode);

                op.putRaw(client, id, content);
                reader.close();
            }
        }
//        FileUtils.forceDelete(new File(targetFolderPath));
//        FileUtils.forceDelete(new File(tarGzFilepath));

    }

}
