package com.dyrnq.service.op;

import com.dyrnq.apisix.domain.*;
import com.dyrnq.apisix.domain.Credential;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

public class Factory {
    public static Op create(String cls) {
        // 特殊处理SSL类
        if (StringUtils.equalsIgnoreCase("ssl", cls)) {
            cls = "SSL";
        }
        Class c = null;
        try {
            c = Class.forName(Route.class.getPackage().getName() + "." + StringUtils.capitalize(cls));
        } catch (ClassNotFoundException e) {
            c = HashMap.class;
        }
        return create(c);
    }

    public static Op create(Class cls) {
        Op obj = null;
        if (cls.equals(Route.class)) {
            return new RouteOp();
        } else if (cls.equals(Upstream.class)) {
            return new UpstreamOp();
        } else if (cls.equals(StreamRoute.class)) {
            return new StreamRouteOp();
        } else if (cls.equals(ConsumerGroup.class)) {
            return new ConsumerGroupOp();
        } else if (cls.equals(Consumer.class)) {
            return new ConsumerOp();
        } else if (cls.equals(GlobalRule.class)) {
            return new GlobalRuleOp();
        } else if (cls.equals(PluginConfig.class)) {
            return new PluginConfigOp();
        } else if (cls.equals(PluginMetadata.class)) {
            return new PluginMetadataOp();
        } else if (cls.equals(Proto.class)) {
            return new ProtoOp();
        } else if (cls.equals(Secret.class)) {
            return new SecretOp();
        } else if (cls.equals(Service.class)) {
            return new ServiceOp();
        } else if (cls.equals(SSL.class)) {
            return new SSLOp();
        }
        return obj;
    }

    public static Sample createSample(String cls) {
        Class c = null;
        try {
            c = Class.forName(Route.class.getPackage().getName() + "." + StringUtils.capitalize(cls));
        } catch (ClassNotFoundException e) {
            c = HashMap.class;
        }
        return createSample(c);
    }

    public static Sample createSample(Class cls) {
        Sample obj = null;
        if (cls.equals(Route.class)) {
            return new RouteOp();
        } else if (cls.equals(Upstream.class)) {
            return new UpstreamOp();
        } else if (cls.equals(StreamRoute.class)) {
            return new StreamRouteOp();
        } else if (cls.equals(ConsumerGroup.class)) {
            return new ConsumerGroupOp();
        } else if (cls.equals(Consumer.class)) {
            return new ConsumerOp();
        } else if (cls.equals(GlobalRule.class)) {
            return new GlobalRuleOp();
        } else if (cls.equals(PluginConfig.class)) {
            return new PluginConfigOp();
        } else if (cls.equals(PluginMetadata.class)) {
            return new PluginMetadataOp();
        } else if (cls.equals(Proto.class)) {
            return new ProtoOp();
        } else if (cls.equals(Secret.class)) {
            return new SecretOp();
        } else if (cls.equals(Service.class)) {
            return new ServiceOp();
        } else if (cls.equals(SSL.class)) {
            return new SSLOp();
        } else if (cls.equals(Credential.class)) {
            return new CredentialOp();
        } else if (cls.equals(HashMap.class)) {
            return new HashMapSample();
        }
        return obj;
    }

    public static class HashMapSample implements Sample {

        @Override
        public Object sample() {
            return new HashMap<>();
        }
    }
}
