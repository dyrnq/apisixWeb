package com.dyrnq;

import cn.hutool.core.util.ReUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.flywaydb.core.Flyway;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Configuration
public class DataSourceEmbed {
    static Logger logger = LoggerFactory.getLogger(DataSourceEmbed.class);
    @Inject("${spring.database.type:}")
    String databaseType;
    @Inject("${spring.datasource.url}")
    String url;
    @Inject("${spring.datasource.username}")
    String username;
    @Inject("${spring.datasource.password}")
    String password;
    @Inject("${solon.app.name}")
    private String projectName;

    @Inject("${project.home:}")
    private String home;

    // typed=true，表示默认数据源。@Db 可不带名字注入
    @Bean(value = "db1", typed = true)
    public DataSource getDataSource() {

        String homeAbsolutePath;
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


        String h2Path = StringUtils.endsWith(homeAbsolutePath, File.separator) ? homeAbsolutePath + "h2" : homeAbsolutePath + File.separator + "h2";
        try {
            FileUtils.forceMkdir(new File(h2Path));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        HikariDataSource ds = null;
        String migrationPath = null;
        if (StringUtils.isBlank(databaseType) || ReUtil.isMatch("(?i)sqlite|h2", databaseType)) {
            HikariConfig dbConfig = new HikariConfig();
            dbConfig.setJdbcUrl("jdbc:h2:" + h2Path + File.separator + "h2;DB_CLOSE_DELAY=1000;DB_CLOSE_ON_EXIT=FALSE");
            dbConfig.setUsername("sa");
            dbConfig.setPassword("");
            dbConfig.setMaximumPoolSize(1);
            dbConfig.setDriverClassName(org.h2.Driver.class.getName());
            ds = new HikariDataSource(dbConfig);
            migrationPath = "classpath:db/migration/h2";
        } else if (ReUtil.isMatch("(?i)my(sql)?", databaseType)) {
            HikariConfig dbConfig = new HikariConfig();
            dbConfig.setJdbcUrl(url);
            dbConfig.setUsername(username);
            dbConfig.setPassword(password);
            dbConfig.setMaximumPoolSize(1);
            dbConfig.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
            ds = new HikariDataSource(dbConfig);
            migrationPath = "classpath:db/migration/mysql";
        } else if (ReUtil.isMatch("(?i)postgres(ql)?|pg(sql)?", databaseType)) {
            HikariConfig dbConfig = new HikariConfig();
            dbConfig.setJdbcUrl(url);
            dbConfig.setUsername(username);
            dbConfig.setPassword(password);
            dbConfig.setMaximumPoolSize(1);
            dbConfig.setDriverClassName(org.postgresql.Driver.class.getName());
            ds = new HikariDataSource(dbConfig);
            migrationPath = "classpath:db/migration/postgresql";
        }
        boolean flaywaySkipMysql5 = false;
        //判断mysql版本，如果是5.多版本则跳过flayway
        Connection conn = null;
        try {
            conn = ds.getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            if (ReUtil.isMatch("(?i).*mysql.*", meta.getDriverName()) && ReUtil.isMatch("^(?i)5\\..*", meta.getDatabaseProductVersion())) {
                flaywaySkipMysql5 = true;
            }
            if (meta instanceof DatabaseMetaData) {

            }
        } catch (SQLException e) {

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }

        }

        if (!flaywaySkipMysql5) {
            Flyway flyway = Flyway.configure()
                    .locations(migrationPath)
                    .baselineOnMigrate(true)
                    .cleanDisabled(true)
                    .dataSource(ds.getJdbcUrl(), ds.getUsername(), ds.getPassword()).load();
            flyway.migrate();
        }

        return ds;
    }

//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}

}
