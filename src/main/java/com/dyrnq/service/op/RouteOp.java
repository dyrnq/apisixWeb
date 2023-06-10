package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.Node;
import com.dyrnq.apisix.domain.Route;
import com.dyrnq.apisix.domain.Timeout;
import com.dyrnq.apisix.domain.Upstream;

import java.util.ArrayList;
import java.util.List;

public class RouteOp implements Op<Route>, Sample {
    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delRoute(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (Route r : client.listRoutes()) {
            client.delRoute(r.getId());
        }
    }

    @Override
    public Route get(AdminClient client, String id) throws ApisixSDKException {
        return client.getRoute(id);
    }

    @Override
    public List<Route> list(AdminClient client) throws ApisixSDKException {
        return client.listRoutes();
    }

    @Override
    public String encodeId(Route obj) {
        return obj.getId();
    }

    @Override
    public Object sample() {
        Route r = new Route();
        r.setName("demo");
        r.setDesc("demo");
        r.setStatus(1);
        r.setUri("/*");
        Upstream upstream = new Upstream();
        upstream.setType("roundrobin");
        upstream.setName("test");
        upstream.setScheme("http");
        upstream.setTimeout(new Timeout(6, 6, 6));
        List<Node> list = new ArrayList<Node>();
        list.add(new Node("127.0.0.1", 18081, 100));
        upstream.setNodes(list);
        r.setUpstream(upstream);
        return r;
    }
}
