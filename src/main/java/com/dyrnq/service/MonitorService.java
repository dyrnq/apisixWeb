package com.dyrnq.service;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.sun.management.OperatingSystemMXBean;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.*;
import oshi.SystemInfo;
/**
 * 获取系统信息的业务逻辑实现类.
 *
 * @author amg * @version 1.0 Creation date: 2008-3-11 - 上午10:06:06
 */
@Component
public class MonitorService {

    OperatingSystemMXBean osmxb;
    SystemInfo systemInfo;
    @Init
    public void afterInjection() {

        osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        systemInfo = new SystemInfo();
    }

    private SystemInfo getSystemInfo(){
        return systemInfo;
    }


    public MonitorInfo getMonitorInfoOshi() {

        MonitorInfo infoBean = new MonitorInfo();
        infoBean.setCpuCount(OshiUtil.getProcessor().getPhysicalProcessorCount());
        infoBean.setThreadCount(OshiUtil.getProcessor().getLogicalProcessorCount());

        infoBean.setUsedMemory(FormatUtil.formatBytes(OshiUtil.getMemory().getTotal() - OshiUtil.getMemory().getAvailable()));
        infoBean.setTotalMemorySize(FormatUtil.formatBytes(OshiUtil.getMemory().getTotal()));

        infoBean.setCpuRatio(NumberUtil.decimalFormat("#.##%", osmxb.getSystemCpuLoad()));
        infoBean.setMemRatio(NumberUtil.decimalFormat("#.##%", NumberUtil.div(OshiUtil.getMemory().getTotal() - OshiUtil.getMemory().getAvailable(), OshiUtil.getMemory().getTotal())));

        return infoBean;
    }

    public List<DiskInfo> getDiskInfo() {
        List<DiskInfo> list = new ArrayList<>();
        for (OSFileStore fs : OshiUtil.getOs().getFileSystem().getFileStores()) {
            DiskInfo diskInfo = new DiskInfo();

            diskInfo.setPath(fs.getMount());
            diskInfo.setUseSpace(FormatUtil.formatBytes(fs.getTotalSpace() - fs.getUsableSpace()));
            diskInfo.setTotalSpace(FormatUtil.formatBytes(fs.getTotalSpace()));
            if (fs.getTotalSpace() != 0) {
                diskInfo.setPercent(NumberUtil.decimalFormat("#.##%", NumberUtil.div(fs.getTotalSpace() - fs.getUsableSpace(), fs.getTotalSpace())));
            } else {
                diskInfo.setPercent(NumberUtil.decimalFormat("#.##%", 0));
            }

            list.add(diskInfo);
        }
        return list;
    }

    public Map getSysInfo() {
        Map cpuInfo = new HashMap();
        Properties props = System.getProperties();
        //操作系统名
        cpuInfo.put("osName", props.getProperty("os.name"));
        //系统架构
        cpuInfo.put("osArch", props.getProperty("os.arch"));
        //服务器名称
        cpuInfo.put("os", String.valueOf(getSystemInfo().getOperatingSystem()));
        //operatingSystem.getFamily();

        try {
			cpuInfo.put("computerName", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
		}

//        //服务器Ip
//        try {
//			cpuInfo.put("computerIp", InetAddress.getLocalHost().getHostAddress());
//		} catch (UnknownHostException e) {
//		}
        //项目路径
        cpuInfo.put("userDir", props.getProperty("user.dir"));
        return cpuInfo;
    }

    public Map getInfo() {
        Map info = new HashMap();
        //info.put("cpuInfo", getCpuInfo());
        info.put("jvmInfo", getJvmInfo());
//        info.put("memInfo", getMemInfo());
        info.put("sysInfo", getSysInfo());
        // info.put("sysFileInfo", getSysFileInfo());
        info.put("jvmProps",System.getProperties());
        return info;
    }

    private static String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

    public Map getJvmInfo() {
        Map jvmInfo = new HashMap();
        Properties props = System.getProperties();
        Runtime runtime = Runtime.getRuntime();
        long jvmTotalMemoryByte = runtime.totalMemory();
        long freeMemoryByte = runtime.freeMemory();
        //jvm总内存
        jvmInfo.put("total", formatByte(runtime.totalMemory()));
        //空闲空间
        jvmInfo.put("free", formatByte(runtime.freeMemory()));
        //jvm最大可申请
        jvmInfo.put("max", formatByte(runtime.maxMemory()));
        //vm已使用内存
        jvmInfo.put("use", formatByte(jvmTotalMemoryByte - freeMemoryByte));
        //jvm内存使用率
        jvmInfo.put("usageRate", new DecimalFormat("#.##%").format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        //jdk版本
        jvmInfo.put("jdkVersion", props.getProperty("java.version"));
        //jdk路径
        jvmInfo.put("jdkHome", props.getProperty("java.home"));
        return jvmInfo;
    }

}