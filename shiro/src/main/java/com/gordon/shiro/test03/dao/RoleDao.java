package com.gordon.shiro.test03.dao;

import com.gordon.shiro.test03.entity.Role;

/**
 * Created by gordon on 2018/9/11.
 */
public interface RoleDao {
    /**创建角色*/
    Role createRole(Role role);
    /**删除角色*/
    void deleteRole(Long roleId);
    /**给角色添加权限*/
    void addPermissionToRole(Long roleId, Long... permissionId);
    /**删除角色拥有的权限*/
    void deletePermissionToRole(Long roleId, Long... permissionId);
}
