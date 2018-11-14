package com.gordon.shiro.test01;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;

/**
 * 自定义Realm
 * Shiro不知道你的用户/权限存储在哪及以何种格式存储；
 * 所以我们一般在应用中都需要实现自己的Realm；
 * Created by gordon on 2018/9/9.
 */
public class MyRealm1 implements Realm {
    @Override
    public String getName() {
        return "myrealm1";
    }

    /**
     * 是否支持AuthenticationToken验证
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持UsernamePasswordToken类型的验证

        return token instanceof UsernamePasswordToken;
    }

    /**
     * 获取身份认证结果信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String name = (String) token.getPrincipal();
        String password = new String((char[])token.getCredentials());

        // 开始验证，这里为了测试直接写死了数据来源zhang 123 。项目中应该从数据库或其他地方获取
        if(!name.equals("zhang")){
            throw new UnknownAccountException("用户名或密码错误");//如果用户名错误
        }
        if(!password.equals("123")){
            throw new IncorrectCredentialsException("用户名或密码错误");//如果密码错误
        }
        // 不要单独写用户名错误或密码错误，防止被恶意扫描
        System.out.println(" ==== MyRealm1 11 执行验证 ====");
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(name, password, getName());
    }
}
