package com.gordon.proxy.dynamicProxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理处理器
 *
 * 它在运行时生成的class，必须提供一组interface给它，
 然后该class就宣称它实现了这些 interface。
 该class的实例可以当作这些interface中的任何一个来用。
 但是这个Dynamic Proxy其实就是一个Proxy， 它不会替你作实质性的工作，
 在生成它的实例时你必须提供一个handler，由它接管实际的工作。

 InvocationHandler：这是调用处理器接口，它自定义了一个 invoke 方法，
 用于集中处理在动态代理类对象上的方法调用，通常在该方法中实现对委托类的代理访问。
 * Created by gordon on 2018/8/2.
 */
public class DynamicSubjectHandler implements InvocationHandler{

    // 真实对象的引用
    private Object obj;

    public DynamicSubjectHandler(Object obj){
        this.obj = obj;
    }

    /**
     *该方法负责集中处理动态代理类上的所有方法调用
     * @param proxy   被代理类实例
     * @param method  被代理类哪个方法
     * @param args    方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + "方法执行之前....");
        Object invoke = method.invoke(obj, args);
        System.out.println(method.getName() + "方法执行之后.......");
        return null;
    }
}
