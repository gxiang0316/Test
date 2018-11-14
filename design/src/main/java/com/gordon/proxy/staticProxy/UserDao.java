package com.gordon.proxy.staticProxy;

/**
 * 目标对象(被代理对象)
 * Created by gordon on 2018/8/2.
 */
public class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("---已保存用户信息-------");
    }
}
