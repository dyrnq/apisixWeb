package com.dyrnq.controller.api;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.profile.Credential;
import com.dyrnq.apisix.profile.DefaultCredential;
import com.dyrnq.apisix.profile.DefaultProfile;
import com.dyrnq.apisix.profile.Profile;
import com.dyrnq.controller.BaseController;
import com.dyrnq.service.BusinessLogic;
import com.dyrnq.service.op.Factory;
import com.dyrnq.service.op.Sample;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Path;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.DownloadedFile;
import org.noear.solon.core.handle.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public void export(Context ctx,String id) throws IOException, ApisixSDKException {
        long currentTimeMillis = System.currentTimeMillis();
        byte[] b = businessLogic.export(currentTimeMillis);
        String fileName = "export-" + currentTimeMillis + ".tar.gz";
        DownloadedFile file = new DownloadedFile("application/octet-stream", b, fileName);
        ctx.outputAsFile(file);
    }
    @Mapping("/import")
    public Result importData(Context ctx, org.noear.solon.core.handle.UploadedFile file ,String id) throws IOException,ApisixSDKException{
        try {
            long currentTimeMillis = System.currentTimeMillis();
            byte[] b = IOUtils.toByteArray(file.getContent());
            this.businessLogic.importData(b, currentTimeMillis);
            return Result.succeed("ok");
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return Result.failure();
    }

    private Gson g() {
        Gson gson = new GsonBuilder()
                .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return "id".equals(f.getName()) || "createTime".equals(f.getName()) || "updateTime".equals(f.getName()); // 如果是特殊字段，则排除
// 其他字段都保留
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson;
    }

    @Mapping("raw")
    public Result raw(Context ctx, String cls, String id) {
        String jsonStr = "{}";
        try {
            jsonStr = g().toJson(Factory.create(cls).get(getAdminClient(), id));
        } catch (Exception Exception) {
        }
        Map map = new HashMap();
        map.put("id", id);
        map.put("rawData", jsonStr);
        return Result.succeed(map);
    }


    @Mapping("{cls}/drop")
    public Result drop(Context ctx, @Path("cls") String cls) {
        try {
            Factory.create(cls).drop(getAdminClient());
        } catch (Exception Exception) {
        }
        return Result.succeed("ok");
    }


    @Mapping("{cls}/sample")
    public Result sample(Context ctx, @Path("cls") String cls) {
        String jsonStr = "{}";
        Sample sample = Factory.createSample(cls);
        jsonStr = g().toJson(sample.sample());
        Map map = new HashMap();
        map.put("rawData", jsonStr);
        return Result.succeed(map);
    }


}
