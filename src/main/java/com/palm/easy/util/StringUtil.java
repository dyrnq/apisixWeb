package com.palm.easy.util;

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

public class StringUtil {
    /**
     * 字符串是否空白
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str==null){
            return true;
        }
        return str.trim().length()==0;
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    public static String leftPad(String srcString, char c, int length) {
        if (srcString == null) {
            srcString = "";
        }

        int tLen = srcString.length();
        if (tLen >= length) {
            return srcString;
        } else {
            int iMax = length - tLen;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < iMax; ++i) {
                sb.append(c);
            }

            sb.append(srcString);
            return sb.toString();
        }
    }
    public static String rightPad(String srcString, char c, int length) {
        if (srcString == null) {
            srcString = "";
        }

        int tLen = srcString.length();
        if (tLen >= length) {
            return srcString;
        } else {
            int iMax = length - tLen;
            StringBuilder sb = new StringBuilder();
            sb.append(srcString);
            for (int i = 0; i < iMax; ++i) {
                sb.append(c);
            }
            return sb.toString();
        }
    }
    public static String readFrom(File file){
        byte []buff=new byte[(int)file.length()];
        FileInputStream fi=null;
        try{
            fi=new FileInputStream(file);
            fi.read(buff);
            return new String(buff,"utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fi!=null){
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String readFrom(InputStream is){
        StringBuilder sb = new StringBuilder();
        try(Reader reader = new InputStreamReader(is,"utf-8")) {
            BufferedReader br = new BufferedReader(reader, 512);
            int value;
            while ((value = br.read()) != -1) {
                sb.append((char) value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void write(File file,String content){
        FileOutputStream fout=null;
        try{
            fout=new FileOutputStream(file);
            fout.write(content.getBytes());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fout!=null){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String join(Object[] array){
        return join(Arrays.asList(array));
    }
    public static String join(Collection<?> list) {
        return StringUtil.join(list, ",");
    }

    public static String join(Collection<?> c, String spliter) {
        if (c == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object name : c) {
            if (first) {
                first = false;
            } else {
                sb.append(spliter);
            }
            sb.append(name);
        }
        return sb.toString();
    }
    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }
    public static String md5Hex(String source){
        try {
            byte[] btInput = source.getBytes("UTF-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            return hex(md);
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        }
    }
    public static String md5Base64(String source){
        try {
            byte[] btInput = source.getBytes("UTF-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            return new String(Base64.getUrlEncoder().encode(md));
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        }
    }

    public static String base64(String source){
       return new String(Base64.getEncoder().encode(source.getBytes()));
    }
    public static String base64Decode(String source){
        return new String(Base64.getUrlDecoder().decode(source.getBytes()));
    }
    public static String base64Hex(String source){
        return hex(Base64.getEncoder().encode(source.getBytes()));
    }
    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String hex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
