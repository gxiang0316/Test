package com.gordon.shiro.test03.dao;

import com.gordon.shiro.test03.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by gordon on 2018/9/11.
 */
public interface UserDao {
    /**创建用户*/
    User createUser(User user);
    /**更新用户*/
    void updateUser(User user);
    /**删除用户*/
    void deleteUser(User user);
    /**给用户添加角色*/
    void addRoleToUser(Long userId, Long... roleId);
    /**删除用户拥有的角色*/
    void deleteRoleToUser(Long userId, Long... roleId);
    /**通过id获取用户*/
    User findUserById(Long userId);
    /**通过用户名获取用户*/
    User findUserByName(String username);
    /**通过用户名获取该用户所有角色*/
    Set<String> findRolesByUsername(String username);
    /**通过用户名获取该用户所有权限*/
    Set<String> findPermissionByUsername(String username);

}
