package com.gordon.shiro.test03.entity;

import java.io.Serializable;

/**
 * Created by gordon on 2018/9/11.
 */
public class User implements Serializable{

    private Long id;
    private String username;
    private String password;
    private String salt;// 加密时用的盐

    private Boolean locked = Boolean.FALSE;//用于是否锁定用户

    // 加密时使用
    public String getCredentialSalt(){
        return username + salt;
    }

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", locked=" + locked +
                '}';
    }
}
