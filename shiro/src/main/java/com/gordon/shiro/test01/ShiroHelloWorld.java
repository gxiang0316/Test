package com.gordon.shiro.test01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gordon on 2018/7/19.
 */
public class ShiroHelloWorld {

    @Test
    public void testHelloWorld() {
        // 获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
        IniSecurityManagerFactory managerFactory = new IniSecurityManagerFactory
                ("classpath:test01/shiro.ini");
        // 得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager manager = managerFactory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        // 得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhang", "123".toCharArray(), true);
        // 开启缓存后，是name最为key保存起来 （用户名必须唯一，那如何解决用户个性化问题？使用昵称，昵称可以不唯一）

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            // 身份验证失败
            e.printStackTrace();
        }

        // 断言 登录是否成功
        Assert.assertEquals(true, subject.isAuthenticated());

        Session session = subject.getSession();
        System.out.println(session.getId());
        System.out.println(subject.getPrincipal());// zhang

        // 设置属性
        session.setAttribute("name", subject.getPrincipal());
        // 获取属性
        session.getAttribute("name");
        Collection<Object> attributeKeys = session.getAttributeKeys();

        Iterator<Object> iterator = attributeKeys.iterator();
        while (iterator.hasNext()) {
            System.out.println("所有的属性KEY ：" + iterator.next());
        }

        // 退出
        subject.logout();

    }

    @After
    public void clearDown() {
        //退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
        System.out.println("解除subject绑定");
    }


    @Test
    public void testCustomRealm() {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory
                ("classpath:test01/MyRealm1.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123".toCharArray(),false);
        subject.login(token);
        Assert.assertEquals(true,subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testCustomMultiRealm(){
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:test01/MyRealm2.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        Session session = subject.getSession();

        UsernamePasswordToken token = new UsernamePasswordToken("wang","123".toCharArray(),false);
        subject.login(token);
        Assert.assertEquals(true,subject.isAuthenticated());
        subject.logout();
        /**
         * 多realm时按照配置的顺序执行，如果前一个验证失败，在源码中有捕获异常，因此myRealm1没有日志打印
         *
         */
    }

    @Test
    public void testJdbcRealm() {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory
                ("classpath:test01/shiro_jdbc.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123".toCharArray(),false);
        subject.login(token);
        Assert.assertEquals(true,subject.isAuthenticated());
        subject.logout();
        /*
            JDBC方式：数据库中的表名必须是users/user_roles/roles_permissions
            原因请看org.apache.shiro.realm.jdbc.JdbcRealm源码
         */
    }
}
