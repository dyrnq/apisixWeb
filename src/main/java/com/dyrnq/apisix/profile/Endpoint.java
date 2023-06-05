package com.dyrnq.apisix.profile;

public class Endpoint {
    private String domain;
    private boolean alive = true;
    private int lastFailureTime = 0;

    public String getDomain() {
        return domain;
    }

    public int getLastFailureTime() {
        return lastFailureTime;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setLastFailureTime(int lastFailureTime) {
        this.lastFailureTime = lastFailureTime;
    }
}
