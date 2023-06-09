package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;

public interface Op {

    void del(AdminClient client, String... id) throws ApisixSDKException;

    void drop(AdminClient client) throws ApisixSDKException;

    Object get(AdminClient client, String id) throws ApisixSDKException;
}
