package com.palm.easy.util;

/**
 * 创建一个短期Token,当服务器重启后，用户根据token自动登录
 */
public class TokenUtil {
    private static final long ONE_YEAR=31536000;//s
//    private static final byte k=8;
    /**
     * 创建一个带时间戳的token
     * @param value
     * @param expire 过期时间(s)
     * @return
     */
    public static String token(String value,String salt,long expire){
        long time=(System.currentTimeMillis()/1000)%ONE_YEAR+expire;
        String t=StringUtil.leftPad(Long.toString(time,36),'0',5);
        String v=value+time+salt;
        return t+StringUtil.md5Base64(v)+StringUtil.base64(value);
    }

//    private static String xor(String str){
//        byte[] data=str.getBytes();
//        for(int i=0;i<data.length;i++){
//            data[i]=(byte)(data[i]^k);
//        }
//        return new String(data);
//    }
    //29位以后都是数据
    public static String getData(String token){
        if(token==null){
            return null;
        }
        //5+24+n
        if(token.length()<30){
            return null;
        }
        return StringUtil.base64Decode(token.substring(29));
    }
    /**
     * 验证token
     * @param token token
     * @return token实际值
     */
    public static boolean validate(String token,String v,String salt){
        if(token==null){
            return false;
        }
        //5+24+n
        if(token.length()<30){
            return false;
        }
        //String v=xor(token.substring(29));
        long t=Long.parseLong(token.substring(0,5),36);
        long now=(System.currentTimeMillis()/1000)%ONE_YEAR;
        if(now>t){
            return false;
        }
        if(StringUtil.md5Base64(v+t+salt).equals(token.substring(5,29))){
            return true;
        }
        return false;
    }
}
