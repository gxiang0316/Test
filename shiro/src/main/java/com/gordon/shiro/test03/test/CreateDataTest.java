package com.gordon.shiro.test03.test;

import com.gordon.shiro.test03.entity.Permission;
import com.gordon.shiro.test03.entity.Role;
import com.gordon.shiro.test03.entity.User;
import com.gordon.shiro.test03.service.PermissionService;
import com.gordon.shiro.test03.service.RoleService;
import com.gordon.shiro.test03.service.UserService;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gordon on 2018/9/12.
 */
public class CreateDataTest {

    private PermissionService permissionService = new PermissionService();
    private RoleService roleService = new RoleService();
    private UserService userService = new UserService();
    public Permission p1;
    public Permission p2;
    public Permission p3;
    public Role role1;
    public Role role2;
    public User user1;
    public User user2;
    public User user3;
    public User user4;


    @Test
    public void createData(){
        // 新增权限
        p1 = new Permission("user:create","用户模块新增",Boolean.TRUE);
        p2 = new Permission("user:update","用户模块修改",Boolean.TRUE);
        p3 = new Permission("menu:create","菜单目录新增",Boolean.TRUE);
        permissionService.createPermission(p1);
        permissionService.createPermission(p2);
        permissionService.createPermission(p3);

        // 新增角色
        role1 = new Role("admin","管理员",Boolean.TRUE);
        role2 = new Role("user","用户管理员",Boolean.TRUE);
        roleService.createRole(role1);
        roleService.createRole(role2);

        // 关联角色-权限
        roleService.addPermissionToRole(role1.getId(),p1.getId());
        roleService.addPermissionToRole(role1.getId(),p2.getId());
        roleService.addPermissionToRole(role2.getId(),p1.getId());
        roleService.addPermissionToRole(role2.getId(),p2.getId());
        roleService.addPermissionToRole(role2.getId(),p3.getId());

        // 新增用户
        user1 = new User("zhang","123456");
        user2 = new User("li","123456");
        user3 = new User("wan","123456");
        user4 = new User("liu","123456");
        user4.setLocked(Boolean.TRUE);

        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);
        userService.createUser(user4);

        // 关联用户-角色
        userService.addRoleToUser(user1.getId(),role1.getId());
        userService.addRoleToUser(user2.getId(),role2.getId());
        userService.addRoleToUser(user3.getId(),role2.getId());
        userService.addRoleToUser(user4.getId(),role2.getId());
    }




}
