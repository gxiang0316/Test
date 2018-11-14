package com.gordon.proxy.dynamicProxy.jdkProxy;

/**
 * Created by gordon on 2018/8/2.
 */
public class JdkProxyTest {


    public static void main(String[] args) {

        // 目标对象
        Subject subject = new RealSubject();

        // 处理器
        DynamicSubjectHandler handler = new DynamicSubjectHandler(subject);

        // 代理对象
        Subject proxy = (Subject) DynamicProxyFactory.getProxyInstance(subject,handler);

        System.out.println("内存中的代理对象 ： " + proxy.getClass());

        proxy.request();

    }



}
