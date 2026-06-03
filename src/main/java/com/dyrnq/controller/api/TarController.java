package com.dyrnq.controller.api;

import com.dyrnq.apisix.ApisixSDKException;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.DownloadedFile;
import org.noear.solon.core.handle.Result;
import org.noear.solon.core.handle.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapping("api/tar")
@Controller
public class TarController extends ApiController {
    static Logger logger = LoggerFactory.getLogger(TarController.class);

    @Mapping("export")
    public void export(Context ctx, String id, String format) throws IOException, ApisixSDKException {
        long currentTimeMillis = System.currentTimeMillis();
        byte[] b = businessLogic.export(id, currentTimeMillis, format);
        String fileName = "export-" + currentTimeMillis + ".tar.gz";
        DownloadedFile file = new DownloadedFile("application/octet-stream", b, fileName);
        ctx.outputAsFile(file);
    }

    @Mapping("import")
    public Result importData(Context ctx, UploadedFile file, String id) throws IOException, ApisixSDKException {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            byte[] b = IOUtils.toByteArray(file.getContent());
            this.businessLogic.importData(id, b, currentTimeMillis);
            return Result.succeed("ok");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failure(e.getMessage());
        }
    }
}
