package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import java.util.List;

public interface Op<T> {

    void del(AdminClient client, String... id) throws ApisixSDKException;

    void drop(AdminClient client) throws ApisixSDKException;

    T get(AdminClient client, String id) throws ApisixSDKException;

    List<T> list(AdminClient client) throws ApisixSDKException;

    String encodeId(T obj);

    T putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException;

    List<T> list(AdminClient client, String[] id) throws ApisixSDKException;

    T put(AdminClient client, T obj) throws ApisixSDKException;
}
