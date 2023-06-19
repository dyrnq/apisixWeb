package com.dyrnq;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;
import com.dyrnq.utils.TarUtils;
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
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class Config {
    static Logger logger = LoggerFactory.getLogger(Config.class);
    @Inject("${solon.app.name}")
    String projectName;
    @Inject("${project.home:}")
    private String home;
    @Inject("${server.session.state.jwt.name:}")
    private String jwtName;

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

    @Bean(value = "homeDir", typed = true)
    HomeDir getHomeDir() {
        String homeAbsolutePath = "";
        String tmpAbsolutePath = "";
        String systemUserDir = SystemUtils.getUserHome().getAbsolutePath();
        if (StringUtils.isBlank(home)) {
            homeAbsolutePath = systemUserDir + File.separator + "." + projectName;
        } else {
            if (StringUtils.startsWith(home, "~")) {
                homeAbsolutePath = RegExUtils.replaceFirst(home, "~", systemUserDir);
            } else {
                homeAbsolutePath = home;
            }
        }
        tmpAbsolutePath = homeAbsolutePath + File.separator + "tmp";

        try {
            FileUtils.forceMkdir(new File(tmpAbsolutePath));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("config***********homeAbsolutePath=" + homeAbsolutePath);
        logger.info("config***********tmpAbsolutePath=" + tmpAbsolutePath);


        String acmeVer = "3.0.6";
        String acmeTarGzFile = "acme.sh-" + acmeVer;
        //https://github.com/acmesh-official/acme.sh/archive/refs/tags/3.0.6.tar.gz
        String acmeShDir = homeAbsolutePath + File.separator + acmeTarGzFile;
        String acmeSh = acmeShDir + File.separator + "acme.sh";
        InputStream inputStream = null;
        try {

            inputStream = new ClassPathResource(acmeTarGzFile + ".tar.gz").getStream();
            FileUtil.writeFromStream(inputStream, tmpAbsolutePath + File.separator + acmeTarGzFile + ".tar.gz");
            FileUtil.mkdir(homeAbsolutePath + File.separator + "acme");

            TarUtils.extractTarGz(tmpAbsolutePath + File.separator + acmeTarGzFile + ".tar.gz", homeAbsolutePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            IoUtil.close(inputStream);
        }

        if (ReUtil.isMatch("(?i).*linux.*", SystemUtil.get(SystemUtil.OS_NAME))) {
            RuntimeUtil.exec("chmod a+x " + acmeSh);
        }

        String acmeHome = homeAbsolutePath +File.separator+ "acme";
        logger.info("config***********acmeShDir=" + acmeShDir);
        logger.info("config***********acmeSh=" + acmeSh);
        logger.info("config***********acmeHome=" + acmeHome);
        return new HomeDir(homeAbsolutePath, tmpAbsolutePath, acmeShDir, acmeSh,acmeHome);
    }

    @Bean(value = "cfgExtractor", typed = true)
    public CfgExtractor getCfgExtractor() {
        String tokenCookieName = StringUtils.isNotBlank(jwtName) ? jwtName : CookieName.NAME_TOKEN;
        logger.info("config***********tokenCookieName=" + tokenCookieName);
        return new CfgExtractor(tokenCookieName);
    }

}