package com.dyrnq;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

@Configuration
public class Config {
    // typed=true，表示默认数据源。@Db 可不带名字注入
    @Bean(value = "db1" ,typed = true)
    public DataSource db1(@Inject("${test.db1}") HikariDataSource ds) throws Exception{

        //DsHelper.initData(ds);

        return ds;
    }





//    @Init
//    public void init(){
//        UserMapper userDao = db1.mapper(UserMapper.class);
//
//        User u = new User();
//        userDao.insert(u,false);
//
//    }



//    @Bean
//    public void db2Init(@Db DbContext db) throws Exception{
//        db.exe("insert into user(id) values(1),(2)");
//    }
}