package com.dyrnq;


import java.io.Serializable;
import java.util.*;

public class UserInfo implements Serializable {
    String username;
    String name;
    transient String password;
    transient String token;
//    List<MenuItem> menu=new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public List<MenuItem> getMenu() {
//        return menu;
//    }
//
//    public void setMenu(List<MenuItem> menu) {
//        this.menu = menu;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
