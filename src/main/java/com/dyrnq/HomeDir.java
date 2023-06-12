package com.dyrnq;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.bean.LifecycleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Component
public class HomeDir implements LifecycleBean {
    static Logger logger = LoggerFactory.getLogger(HomeDir.class);
    @Inject("${project.home:}")
    private String home;

    @Inject("${solon.app.name}")
    String projectName;
    @Inject
    CfgExtractor cfgExtractor;
    private String homeAbsolutePath;
    private String tmpAbsolutePath;

    @Override
    public void start() throws Throwable {
        String systemUserDir = SystemUtils.getUserHome().getAbsolutePath();
        if(StringUtils.isBlank(home)){
            homeAbsolutePath=systemUserDir + File.separator+"."+projectName;
        }else{
            if(StringUtils.startsWith(home,"~")){
                homeAbsolutePath= RegExUtils.replaceFirst(home,"~",systemUserDir);
            }else{
                homeAbsolutePath=home;
            }
        }
        tmpAbsolutePath= homeAbsolutePath+File.separator+"tmp";

        try{
            FileUtils.forceMkdir(new File(tmpAbsolutePath));
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }


    public String getHomeAbsolutePath(){
        return homeAbsolutePath;
    }
    public String getTmpAbsolutePath(){
        return tmpAbsolutePath;
    }
}
