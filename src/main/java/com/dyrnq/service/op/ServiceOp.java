package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Service;
import java.util.ArrayList;
import java.util.List;

public class ServiceOp implements Op<Service>, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delService(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Service r : client.listServices()) {
            client.delService(r.getId());
        }
    }

    @Override
    public Service put(AdminClient client, Service obj) throws ApisixSDKException {
        return client.putService(obj.getId(), obj);
    }

    @Override
    public Service get(AdminClient client, String id) throws ApisixSDKException {
        return client.getService(id);
    }

    @Override
    public List<Service> list(AdminClient client) throws ApisixSDKException {
        return client.listServices();
    }

    @Override
    public String encodeId(Service obj) {
        return obj.getId();
    }

    @Override
    public Service putRaw(AdminClient client, String id, String rawData) throws ApisixSDKException {
        return client.putServiceRaw(id, rawData);
    }

    @Override
    public List<Service> list(AdminClient client, String[] id) throws ApisixSDKException {
        List<Service> list = new ArrayList<>();
        for (String i : id) {
            list.add(get(client, i));
        }
        return list;
    }

    @Override
    public Object sample() {
        return null;
    }
}
