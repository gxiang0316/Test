package com.gordon.proxy.dynamicProxy.jdkProxy;

/**
 * 真实角色
 * Created by gordon on 2018/8/2.
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("---- 管理员查询所有图书记录------");
    }
}
