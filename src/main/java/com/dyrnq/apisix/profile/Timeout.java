package com.dyrnq.apisix.profile;

public class Timeout {
    private final int read;
    private final int write;
    private final int conn;

    public Timeout(int read, int write, int conn) {
        this.read = read;
        this.write = write;
        this.conn = conn;
    }

    public int getRead() {
        return read;
    }

    public int getWrite() {
        return write;
    }

    public int getConn() {
        return conn;
    }
}
