package com.dyrnq;

import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;


public class AuthHandler implements Handler {
    @Override
    public void handle(Context ctx) throws Throwable {
        System.out.println("ok");
    }
}
