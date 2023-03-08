package com.dyrnq;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

@Configuration
public class Config {
    // typed=true，表示默认数据源。@Db 可不带名字注入
    @Bean(value = "db1" ,typed = true)
    public DataSource db1(@Inject("${test.db1}") HikariDataSource ds) throws Exception{
        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .cleanDisabled(true)
                .dataSource(ds.getJdbcUrl(), ds.getUsername(), ds.getPassword()).load();
        flyway.migrate();

        return ds;
    }


}