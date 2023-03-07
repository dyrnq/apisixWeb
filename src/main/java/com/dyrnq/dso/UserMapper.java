package com.dyrnq.dso;

import com.dyrnq.model.User;
import org.noear.wood.BaseMapper;
import org.noear.wood.annotation.Db;


@Db("db1")
public interface UserMapper extends BaseMapper<User> {

}
