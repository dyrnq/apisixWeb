package com.dyrnq.dso;

import com.dyrnq.model.Cert;
import com.dyrnq.model.Deploy;
import org.noear.wood.BaseMapper;
import org.noear.wood.annotation.Db;

@Db("db1")
public interface DeployMapper extends BaseMapper<Deploy> {
}
