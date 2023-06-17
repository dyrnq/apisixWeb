package com.dyrnq;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.i18n.LocaleResolver;
import org.noear.solon.i18n.impl.LocaleResolverCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Configuration
public class Config {
    static Logger logger = LoggerFactory.getLogger(Config.class);
    // typed=true，表示默认数据源。@Db 可不带名字注入
//    @Bean(value = "db1" ,typed = true)
//    public DataSource db1(@Inject("${test.db1}") HikariDataSource ds) throws Exception{
//        Flyway flyway = Flyway.configure()
//                .baselineOnMigrate(true)
//                .cleanDisabled(true)
//                .dataSource(ds.getJdbcUrl(), ds.getUsername(), ds.getPassword()).load();
//        flyway.migrate();
//
//        return ds;
//    }
    @Bean
    public LocaleResolver localInit() {
        return new LocaleResolverCookie();
    }
    @Inject("${project.home:}")
    private String home;

    @Inject("${solon.app.name}")
    String projectName;

    @Bean(value = "homeDir", typed = true)
    HomeDir getHomeDir(){
        String homeAbsolutePath="";
        String tmpAbsolutePath="";
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
        logger.info("homeAbsolutePath="+homeAbsolutePath);
        logger.info("tmpAbsolutePath="+tmpAbsolutePath);
        return new HomeDir(homeAbsolutePath,tmpAbsolutePath);
    }

}