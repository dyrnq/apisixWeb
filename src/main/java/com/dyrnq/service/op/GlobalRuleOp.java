package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.GlobalRule;

import java.util.List;

public class GlobalRuleOp implements Op<GlobalRule>, Sample {

    @Override
    public void del(AdminClient client, String... id) throws ApisixSDKException {
        for (String i : id) {
            client.delGlobalRule(i);
        }
    }

    @Override
    public void drop(AdminClient client) throws ApisixSDKException {
        for (GlobalRule r : client.listGlobalRules()) {
            client.delGlobalRule(r.getId());
        }
    }

    @Override
    public GlobalRule get(AdminClient client, String id) throws ApisixSDKException {
        return client.getGlobalRule(id);
    }

    @Override
    public List<GlobalRule> list(AdminClient client) throws ApisixSDKException {
        return client.listGlobalRules();
    }

    @Override
    public String encodeId(GlobalRule obj) {
        return obj.getId();
    }

    @Override
    public Object sample() {
        return null;
    }
}
