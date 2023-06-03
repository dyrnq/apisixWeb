package com.dyrnq.service;

import com.dyrnq.dso.UserMapper;
import com.dyrnq.model.User;
import org.noear.solon.annotation.ProxyComponent;
import org.noear.wood.MapperWhereQ;
import org.noear.wood.annotation.Db;
import org.noear.wood.ext.Act1;

import java.util.List;

@ProxyComponent
public class BusinessLogic {
    @Db
    UserMapper userMapper;


    public User login(String name, String pass){
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("name",name).andEq("pass",pass);
        };

        List<User> list = userMapper.selectList(condition);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public User findByName(String name){
        Act1<MapperWhereQ> condition = mapperWhereQ -> {
            mapperWhereQ.whereEq("name",name);
        };

        List<User> list = userMapper.selectList(condition);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

}
