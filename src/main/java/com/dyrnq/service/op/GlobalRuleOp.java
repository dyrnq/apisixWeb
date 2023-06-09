package com.dyrnq.service.op;

import com.dyrnq.apisix.AdminClient;
import com.dyrnq.apisix.ApisixSDKException;
import com.dyrnq.apisix.domain.GlobalRule;

public class GlobalRuleOp implements Op, Sample {

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
    public Object get(AdminClient client, String id) throws ApisixSDKException {
        return client.getGlobalRule(id);
    }

    @Override
    public Object sample() {
        return null;
    }
}
