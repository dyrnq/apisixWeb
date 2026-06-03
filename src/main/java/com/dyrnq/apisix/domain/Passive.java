package com.dyrnq.apisix.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class Passive {

    @SerializedName("unhealthy")
    @Expose
    private Unhealthy unhealthy;

    @SerializedName("healthy")
    @Expose
    private Healthy healthy;

    @SerializedName("type")
    @Expose
    private Type type = Type.fromValue("http");

    public Unhealthy getUnhealthy() {
        return unhealthy;
    }

    public void setUnhealthy(Unhealthy unhealthy) {
        this.unhealthy = unhealthy;
    }

    public Healthy getHealthy() {
        return healthy;
    }

    public void setHealthy(Healthy healthy) {
        this.healthy = healthy;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        @SerializedName("http")
        HTTP("http"),
        @SerializedName("https")
        HTTPS("https"),
        @SerializedName("tcp")
        TCP("tcp");
        private static final Map<String, Type> CONSTANTS = new HashMap<String, Type>();

        static {
            for (Type c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public static Type fromValue(String value) {
            Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }
    }
}
