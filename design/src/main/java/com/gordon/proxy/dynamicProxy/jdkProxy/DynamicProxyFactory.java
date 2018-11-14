package com.gordon.proxy.dynamicProxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 创建动态代理对象
 * Created by gordon on 2018/8/2.
 */
public class DynamicProxyFactory {

    /**
     * 创建代理对象
     * @param obj      目标类
     * @param handler  处理器
     * @return
     */
    public static Object getProxyInstance(Object obj, InvocationHandler handler){
        ClassLoader classLoader = obj.getClass().getClassLoader();
        Class<?>[] interfaces = obj.getClass().getInterfaces();

        /**
         * static Object newProxyInstance(ClassLoader loader, Class [] interfaces, InvocationHandler handler)
         * 注意该方法是在Proxy类中是静态方法,且接收的三个参数依次为:
                ClassLoader loader:指定当前目标对象使用类加载器,用null表示默认类加载器
                Class [] interfaces:需要实现的接口数组
                InvocationHandler handler:调用处理器,执行目标对象的方法时,
                    会触发调用处理器的方法,从而把当前执行目标对象的方法作为参数传入
         */
        Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, handler);

        return proxyInstance;

    }

}
