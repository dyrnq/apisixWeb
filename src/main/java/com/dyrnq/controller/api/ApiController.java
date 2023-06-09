package com.dyrnq.controller.api;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.DefaultCredential;
import com.dyrnq.apisix.profile.DefaultProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.controller.BaseController;
import com.dyrnq.service.BusinessLogic;
import com.google.gson.*;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.DownloadedFile;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mapping("api")
@Controller
public class ApiController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Inject
    BusinessLogic businessLogic;

    protected AdminClient getAdminClient() {
        String url = "192.168.66.100:9180";
        Credential c = new DefaultCredential("edd1c9f034335f136f87ad84b625c8f1");
        Profile p = DefaultProfile.getProfile(url, "", c);
        AdminClient client = new AdminClient(p);
        return client;
    }

    @Mapping("/export")
    public void export(Context ctx) throws IOException, ApisixSDKException {
        long currentTimeMillis = System.currentTimeMillis();
        byte[] b = businessLogic.export(currentTimeMillis);
        String fileName = "export-" + currentTimeMillis + ".tar.gz";
        DownloadedFile file = new DownloadedFile("application/octet-stream", b, fileName);
        ctx.outputAsFile(file);
    }

    @Mapping("raw")
    public Result getRaw(Context ctx, String cls, String id) {

        String jsonStr = "{}";
        Gson gson = new GsonBuilder()
                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        if ("id".equals(f.getName()) || "createTime".equals(f.getName()) || "updateTime".equals(f.getName())) {
                            return true; // 如果是特殊字段，则排除
                        }
                        return false; // 其他字段都保留
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        try {
            switch (cls) {
                case "route":
                    jsonStr = gson.toJson(getAdminClient().getRoute(id));
                    break;
                case "upstream":
                    jsonStr = gson.toJson(getAdminClient().getUpstream(id));
                    break;
                case "ssl":
                    jsonStr = gson.toJson(getAdminClient().getSSL(id));
                    break;
                case "service":
                    jsonStr = gson.toJson(getAdminClient().getService(id));
                    break;
                case "streamRoute":
                    jsonStr = gson.toJson(getAdminClient().getStreamRoute(id));
                    break;
                case "secret":
                    jsonStr = gson.toJson(getAdminClient().getSecret(id));
                    break;
                case "consumer":
                    jsonStr = gson.toJson(getAdminClient().getConsumer(id));
                    break;
                case "globalRule":
                    jsonStr = gson.toJson(getAdminClient().getGlobalRule(id));
                    break;
                case "pluginConfig":
                    jsonStr = gson.toJson(getAdminClient().getPluginConfig(id));
                    break;
                case "pluginMetadata":
                    jsonStr = gson.toJson(getAdminClient().getPluginMetadata(id));
                    break;
                case "consumerGroup":
                    jsonStr = gson.toJson(getAdminClient().getConsumerGroup(id));
                    break;
                case "plugin":
                    jsonStr = gson.toJson(getAdminClient().getPlugin(id));
                    break;
                case "proto":
                    jsonStr = gson.toJson(getAdminClient().getProto(id));
                    break;

            }
        } catch (Exception Exception) {
        }
        Map map = new HashMap();
        map.put("id", id);
        map.put("rawData", jsonStr);
        return Result.succeed(map);
    }


    @Mapping("drop")
    public Result drop(Context ctx, String cls) {

        try {
            switch (cls) {
                case "route":
                    for (Route r : getAdminClient().listRoutes()) {
                        getAdminClient().delRoute(r.getId());
                    }
                    break;
                case "upstream":
                    for (Upstream r : getAdminClient().listUpstreams()) {
                        getAdminClient().delUpstream(r.getId());
                    }
                    break;
                case "ssl":
                    for (SSL r : getAdminClient().listSSLs()) {
                        getAdminClient().delSSL(r.getId());
                    }
                    break;
                case "service":
                    for (Service r : getAdminClient().listServices()) {
                        getAdminClient().delService(r.getId());
                    }
                    break;
                case "streamRoute":
                    for (StreamRoute r : getAdminClient().listStreamRoutes()) {
                        getAdminClient().delStreamRoute(r.getId());
                    }
                    break;
                case "secret":
                    for (Secret r : getAdminClient().listSecrets()) {
                        getAdminClient().delSecret(r.getId());
                    }
                    break;
                case "consumer":
                    for (Consumer r : getAdminClient().listConsumers()) {
                        getAdminClient().delConsumer(r.getUsername());
                    }
                    break;
                case "globalRule":
                    for (GlobalRule r : getAdminClient().listGlobalRules()) {
                        getAdminClient().delGlobalRule(r.getId());
                    }
                    break;
                case "pluginConfig":
                    for (PluginConfig r : getAdminClient().listPluginConfigs()) {
                        getAdminClient().delPluginConfig(r.getId());
                    }
                    break;
                case "consumerGroup":
                    for (ConsumerGroup r : getAdminClient().listConsumerGroups()) {
                        getAdminClient().delConsumerGroup(r.getId());
                    }
                    break;
                case "proto":
                    for (Proto r : getAdminClient().listProtos()) {
                        getAdminClient().delProto(r.getId());
                    }
                    break;
            }
        } catch (Exception Exception) {
        }

        return Result.succeed("ok");
    }

}
