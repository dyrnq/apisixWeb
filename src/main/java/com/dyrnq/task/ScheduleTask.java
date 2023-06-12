package com.dyrnq.task;

import com.dyrnq.HomeDir;
import org.apache.commons.io.FileUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Component
public class ScheduleTask {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    // 续签证书
    @Scheduled(cron = "0 0 2 * * ?")
    public void certTasks() {

    }

    @Inject
    HomeDir homeDir;

    //清理tmp目录,30s执行一次,清理一小时之前的数据
    @Scheduled(cron = "0/30 * * * * ?")
    public void clearTmp(){
        try {
            File file = new File(homeDir.getTmpAbsolutePath());
            File[] list = file.listFiles();
            long curr = System.currentTimeMillis();
            for (File f : list) {
                long last = f.lastModified();

                if (curr - last > 1L * 60L * 60L * 1000) {
                    FileUtils.forceDelete(f);
                }
            }
        }catch (Exception e){

        }
    }

    // 测试任务
//	@Scheduled(cron = "* * * * * ?")
//	public void test() throws InterruptedException {
//		Thread.sleep(3000);
//		System.out.println(DateUtil.format(new Date(), "HHmmss"));
//	}
}
