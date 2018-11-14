package com.gordon.shiro.test03.service;

import com.gordon.shiro.test03.dao.UserDao;
import com.gordon.shiro.test03.dao.UserDaoImpl;
import com.gordon.shiro.test03.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by gordon on 2018/9/11.
 */
public class UserService {

    private UserDao userDao = new UserDaoImpl();
    private PasswordHelper passwordHelper = new PasswordHelper();

    /**
     * 创建用户
     */
    public User createUser(User user) {
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    /**
     * 更新用户
     */
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    /**
     * 修改密码
     * @param userId
     * @param password
     */
    public void changePassword(Long userId,String password){
        User user = userDao.findUserById(userId);
        if(user != null){
            user.setPassword(password);
            passwordHelper.encryptPassword(user);
            updateUser(user);
        }
    }

    /**
     * 删除用户
     */
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    /**
     * 给用户添加角色
     */
    public void addRoleToUser(Long userId, Long... roleId) {
        userDao.addRoleToUser(userId,roleId);
    }

    /**
     * 删除用户拥有的角色
     */
    public void deleteRoleToUser(Long userId, Long... roleId) {
        userDao.deleteRoleToUser(userId,roleId);
    }

    /**
     * 通过id获取用户
     */
    public User findUserById(Long userId) {
        return userDao.findUserById(userId);
    }

    /**
     * 通过用户名获取用户
     */
    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }

    /**
     * 通过用户名获取该用户所有角色
     */
    public Set<String> findRolesByUsername(String username) {
        return userDao.findRolesByUsername(username);
    }

    /**
     * 通过用户名获取该用户所有权限
     */
    public Set<String> findPermissionByUsername(String username) {
        return userDao.findPermissionByUsername(username);
    }


}
