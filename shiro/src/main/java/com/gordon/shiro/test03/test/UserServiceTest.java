package com.gordon.shiro.test03.test;

import com.gordon.shiro.test03.entity.Permission;
import com.gordon.shiro.test03.entity.Role;
import com.gordon.shiro.test03.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 测试前先执行CreateDataTest创建数据
 * Created by gordon on 2018/9/12.
 */
public class UserServiceTest extends CreateDataTest{

//    user1 = new User("zhang","123456");
//    user2 = new User("li","123456");
//    user3 = new User("wan","123456");
//    user4 = new User("liu","123456");

    @Test
    public void testLoginSuccess(){
        login("zhang","123456");
        Assert.assertEquals(true,getSubject().isAuthenticated());
    }

    public void login(String username,String password){
        IniSecurityManagerFactory factory =
                new IniSecurityManagerFactory("classpath:test03/shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);

    }

    public Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    @After
    public void unbindSubject(){
        ThreadContext.unbindSubject();
    }
}
