package com.dyrnq.cert.acme;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


@Component
public class AcmeshCmd {
    static Logger logger = LoggerFactory.getLogger(AcmeshCmd.class);

    public String execCMD(String cmd, String[] envs, long timeout) {
        Process process = null;
        String sbStd = "";

        String[] allEnvs = ArrayUtil.addAll(System.getenv() //
                .entrySet()//
                .stream()//
                .map(r -> String.format("%s=%s", r.getKey(), r.getValue()))//
                .toArray(String[]::new), envs);

//        long start = System.currentTimeMillis();
        try {
            process = RuntimeUtil.exec(allEnvs, "/bin/sh", "-c", cmd);

            while (true) {
                if (process.isAlive()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {

                    }
                } else {
                    break;
                }
            }
            logger.info("process.exitValue()=" + process.exitValue());
            if (process.exitValue() == 0) {
                sbStd = RuntimeUtil.getResult(process);
            } else {
                String error = RuntimeUtil.getErrorResult(process);
                if (StringUtils.isNotBlank(error)) {
                    throw new RuntimeException(error);
                }
            }
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        return sbStd;
    }
}
