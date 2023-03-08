package com.dyrnq;


import com.dyrnq.UserInfo;
import com.palm.easy.util.StringUtil;
import com.palm.easy.util.TokenUtil;
import org.noear.solon.Utils;
import org.noear.solon.core.handle.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public final class Current {
    private static final String userKeyAttrName = "@user_token";
    private static final long timeOut=30*60*1000;
    private static Map<String,CacheUser> userMap=new ConcurrentHashMap<>();
    static {
//        Utils.scheduled.schedule(()->{
//            long now=System.currentTimeMillis();
//            userMap.forEach((k,v)->{
//                if(v.time<now){
//                    userMap.remove(k);
//                }
//            });
//        },1, TimeUnit.SECONDS);
    }
    static Context context() {
        return Context.current();
    }
    public static String getUserToken(Context ctx) {
        String userToken = ctx.attr(userKeyAttrName);
        if (StringUtil.isEmpty(userToken)) {
            userToken = ctx.header("token");
            if (StringUtil.isNotEmpty(userToken)) {
                ctx.attrSet(userKeyAttrName, userToken);
            }
        }
        return userToken;
    }
    public static UserInfo user() {
        Context ctx=context();
        String token=getUserToken(ctx);
        if(token==null){
            return null;
        }
        CacheUser cu=userMap.get(token);
        if(cu==null){
            return null;
        }
        UserInfo ui=cu.userInfo;
        if(System.currentTimeMillis()>cu.time-300*1000){//还有不足5分钟时续期
            userMap.remove(token);
            String newTk=TokenUtil.token(ui.getUsername(),ui.getPassword(),timeOut/1000);
            ui.setToken(newTk);
            ctx.headerSet("token",newTk);
            ctx.attrSet(userKeyAttrName,newTk);
            cu.time=System.currentTimeMillis()+timeOut;
        }
        return cu.userInfo;
    }
    public static void setUser(UserInfo userInfo) {
        Context ctx=context();
        CacheUser cu=new CacheUser(System.currentTimeMillis()+timeOut,userInfo);
        String newTk=TokenUtil.token(userInfo.getUsername(),userInfo.getPassword(),timeOut/1000);
        userMap.put(newTk,cu);
        ctx.headerSet("token",newTk);
        ctx.attrSet(userKeyAttrName,newTk);
    }
    public static void logout(Context ctx){
        String token = getUserToken(ctx);
        if(StringUtil.isNotEmpty(token)){
            userMap.remove(token);
            context().headerSet("rtk","true");
        }
    }
    static class CacheUser{
        long time;
        UserInfo userInfo;

        public CacheUser(long time, UserInfo userInfo) {
            this.time = time;
            this.userInfo = userInfo;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }
    }
}
