package com.gordon.shiro.test01;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by gordon on 2018/9/9.
 */
public class MyRealm2 implements Realm {
    @Override
    public String getName() {
        return "myrealm2";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        if(!username.equals("wang")){
            throw new UnknownAccountException("用户名或密码错误");
        }
        if(!password.equals("123")){
            throw new IncorrectCredentialsException("用户名或密码错误");
        }
        System.out.println(" ==== MyRealm2 22 执行验证 ====");
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
