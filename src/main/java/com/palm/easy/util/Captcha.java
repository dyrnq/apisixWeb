package com.palm.easy.util;

import org.noear.solon.core.handle.Context;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class Captcha {
    protected Font font = new Font("Verdana", Font.ITALIC, 24);   // 字体
    protected int len = 5;  // 验证码随机字符长度
    protected int width = 150;  // 验证码显示跨度
    protected int height = 40;  // 验证码显示高度
    private String chars = null;  // 随机字符串
    public Captcha(){

    }
    public Captcha(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Captcha(int width, int height, int len){
        this(width,height);
        this.len = len;
    }

    public Captcha(int width, int height, int len, Font font)
    {
        this(width,height,len);
        this.font = font;
    }
    /**
     * 生成随机字符数组
     * @return 字符数组
     */
    protected char[] alphas()
    {
        if(chars!=null){
            return chars.toCharArray();
        }
        char[] cs = new char[len];
        for(int i = 0;i<len;i++)
        {
            cs[i] = Randoms.alpha();
        }
        chars = new String(cs);
        return cs;
    }
    public Font getFont()
    {
        return font;
    }

    public void setFont(Font font)
    {
        this.font = font;
    }

    public int getLen()
    {
        return len;
    }

    public void setLen(int len)
    {
        this.len = len;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * 给定范围获得随机颜色
     * @return Color 随机颜色
     */
    protected Color color(int fc, int bc)
    {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + Randoms.num(bc - fc);
        int g = fc + Randoms.num(bc - fc);
        int b = fc + Randoms.num(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 验证码输出,抽象方法，由子类实现
     * @param os 输出流
     */
    public void out(OutputStream os){
        int fontSize = width / 3;
        int wordPlace = height / 2 + fontSize / 3;
        BufferedImage image = new BufferedImage(width, height, 2);
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Serif", 2, fontSize));
        int i = 0;
        while (i < 4) {
            g.setColor(new Color(20 + this.rand(110), 20 + this.rand(110), 20 + this.rand(110)));
            int x = this.rand(width);
            int y = this.rand(height);
            int xl = this.rand(width);
            int yl = this.rand(height);
            g.drawLine(x, y, x + xl, y + yl);
            ++i;
        }
        int randLen = len;
        String sRand = text().toLowerCase();//new StringBuffer(randLen);
        int i2 = 0;
        while (i2 < randLen) {
            String rand = sRand.charAt(i2)+"";
            g.setColor(new Color(20 + this.rand(110), 20 + this.rand(110), 20 + this.rand(110)));
            g.drawString(rand, fontSize * i2 / 2 + fontSize / 4, wordPlace);
            ++i2;
        }
        g.dispose();
        try {
            ImageIO.write(image, "png",os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int rand(int max) {
        return Randoms.num(max + 1); }

    /**
     * 获取随机字符串
     * @return string
     */
    public String text()
    {
        if(chars==null){
            alphas();
        }
        return chars;
    }

    private static final String captchaSessionName="_captcha";
    public static long expiration = 600; //过期时间10分钟
    static {
        System.setProperty("java.awt.headless","true");
    }


    /**
     * 输出验证码
     * @param ctx
     */
    public static void captcha(Context ctx){
        Captcha captcha=new Captcha();
        ctx.sessionSet(captchaSessionName,captcha.text());
        ctx.sessionSet(captchaSessionName+"_date",System.currentTimeMillis()/1000+expiration);
        try {
            ctx.contentType("image/png");
            ctx.headerSet("Cache-Control","no-cache");
            captcha.out(ctx.outputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证验证码
     * @param captcha
     * @param ctx
     * @return
     */
    public static boolean verify(String captcha,Context ctx){
        if(StringUtil.isEmpty(captcha)){
            return false;
        }
        Object cacheCaptcha = ctx.session(captchaSessionName);
        long expirationTime=ctx.session(captchaSessionName+"_date",0L);
        ctx.sessionSet(captchaSessionName,"");
        if(captcha.toLowerCase().equals(cacheCaptcha)){
            return System.currentTimeMillis()/1000<expirationTime;
        }
        return false;
    }

}
