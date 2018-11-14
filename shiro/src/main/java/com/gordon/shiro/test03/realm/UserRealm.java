package com.gordon.shiro.test03.realm;


import com.gordon.shiro.test03.entity.User;
import com.gordon.shiro.test03.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Created by gordon on 2018/9/12.
 */
public class UserRealm extends AuthorizingRealm {

    private UserService userService = new UserService();

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        // principalCollection.asList(); 获取多个
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取角色
        authorizationInfo.setRoles(userService.findRolesByUsername(username));
        // 获取权限
        authorizationInfo.setStringPermissions(userService.findPermissionByUsername(username));

        return authorizationInfo;
    }

    /**
     * 获取验证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        String username = (String) token.getPrincipal();
        // 查询数据库
        User user = userService.findUserByName(username);
        if(user == null){
            throw new UnknownAccountException("该用户不存在");// 账号错误
        }

        if(user.getLocked()){
            throw new LockedAccountException("账号被锁定");
        }

        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，也可以自己实现matcher
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
