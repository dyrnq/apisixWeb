package com.palm.easy.util;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

@SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
public class IDUtil {
    static final SecureRandom numberGenerator = new SecureRandom();

    public static String toString(long id) {
        return Long.toString(id, Character.MAX_RADIX);
    }
    private static byte[] getRandomBytes() {
        byte[] randomBytes = new byte[16];
        numberGenerator.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f;  /* clear version        */
        randomBytes[6] |= 0x40;  /* set to version 4     */
        randomBytes[8] &= 0x3f;  /* clear variant        */
        randomBytes[8] |= 0x80;  /* set to IETF variant  */
        return randomBytes;
    }
    /**
     * java uuid 转换为36进制的短uuid,长度为24位
     *
     * @return
     */
    public static String getUUID() {
        return new BigInteger(1, getRandomBytes()).toString(Character.MAX_RADIX);
    }
    /**
     * base64 UUID,长度为22位,生成速度更快,但不适合用于数据库id(有大小写和符号)
     * @return
     */
    public static String base64UUID() {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(getRandomBytes());
    }

}
