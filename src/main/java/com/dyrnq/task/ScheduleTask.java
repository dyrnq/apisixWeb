package com.dyrnq.task;

import com.dyrnq.HomeDir;
import com.dyrnq.dso.CertMapper;
import com.dyrnq.model.Cert;
import com.dyrnq.service.CertService;
import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.scheduling.annotation.Scheduled;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.ext.Act1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ScheduleTask {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    CertMapper certMapper;

    @Inject
    CertService certService;

    @Inject
    HomeDir homeDir;

    // 续签证书
    @Scheduled(cron = "0 0 2 * * ?")
    public void certTasks() {
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("renew", 1);
        };
        List<Cert> list = certMapper.selectList(condition);
        Long now = System.currentTimeMillis();
        for (Cert cert : list) {
            Long exp = cert.getNotAfter();
            // 提前6天覆盖证书
            Long exp1 = Long.sum(now, 6 * 24 * 60 * 60 * 1000L);
            if (exp > exp1) {
                try {
                    certService.renew(cert);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    // 清理tmp目录,30s执行一次,清理十小时之前的数据
    @Scheduled(cron = "0/30 * * * * ?")
    public void clearTmp() {
        try {
            File file = new File(homeDir.getTmpAbsolutePath());
            File[] list = file.listFiles();
            long curr = System.currentTimeMillis();
            for (File f : list) {
                long last = f.lastModified();

                if (curr - last > 10L * 60L * 60L * 1000) {
                    FileUtils.forceDelete(f);
                }
            }
        } catch (Exception e) {

        }
    }

    // 测试任务
    //	@Scheduled(cron = "* * * * * ?")
    //	public void test() throws InterruptedException {
    //		Thread.sleep(3000);
    //		System.out.println(DateUtil.format(new Date(), "HHmmss"));
    //	}
}
