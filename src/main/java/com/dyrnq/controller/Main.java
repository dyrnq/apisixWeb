package com.dyrnq.controller;

import org.noear.solon.sessionstate.jwt.JwtUtils;

public class Main {
    public static void main(String args[]){
        System.out.println(JwtUtils.createKey());
    }
}
