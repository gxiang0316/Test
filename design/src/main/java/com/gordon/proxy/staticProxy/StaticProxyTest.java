package com.gordon.proxy.staticProxy;

/**
 * 静态代理总结:
 优点：
    可以做到在不修改目标对象的功能前提下,对目标功能扩展.

 缺点:
 　　代理类和委托类实现相同的接口，同时要实现相同的方法。
    这样就出现了大量的代码重复。如果接口增加一个方法，
    除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法。
    增加了代码维护的复杂度。
 * Created by gordon on 2018/8/2.
 */
public class StaticProxyTest {

    public static void main(String[] args) {
        // 目标对象
        UserDao userDao = new UserDao();

        // 代理对象
        UserDaoProxy proxy = new UserDaoProxy(userDao);

        // 执行代理对象方法，实际上执行的核心方法是目标对象方法，
        // 但是我们可以在代理对象方法中扩展
        proxy.save();

    }

}
