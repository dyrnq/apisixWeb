package com.dyrnq.dso;

import com.dyrnq.model.Cert;
import org.noear.wood.BaseMapper;
import org.noear.wood.annotation.Db;

@Db("db1")
public interface CertMapper extends BaseMapper<Cert> {
}