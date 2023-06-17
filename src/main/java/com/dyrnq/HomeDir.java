package com.dyrnq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeDir {
    static Logger logger = LoggerFactory.getLogger(HomeDir.class);

    private String homeAbsolutePath;
    private String tmpAbsolutePath;

    public HomeDir(){
        super();
    }
    public HomeDir(String homeAbsolutePath,String tmpAbsolutePath){
        this.homeAbsolutePath = homeAbsolutePath;
        this.tmpAbsolutePath = tmpAbsolutePath;
    }

    public String getHomeAbsolutePath() {
        return homeAbsolutePath;
    }

    public String getTmpAbsolutePath() {
        return tmpAbsolutePath;
    }
}
