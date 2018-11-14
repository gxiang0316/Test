package com.gordon.shiro.test03.service;

import com.gordon.shiro.test03.dao.RoleDao;
import com.gordon.shiro.test03.dao.RoleDaoImpl;
import com.gordon.shiro.test03.entity.Role;

/**
 * Created by gordon on 2018/9/11.
 */
public class RoleService {

    private RoleDao roleDao = new RoleDaoImpl();


    /**
     * 创建角色
     */
    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    /**
     * 删除角色
     */
    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    /**
     * 给角色添加权限
     */
    public void addPermissionToRole(Long roleId, Long... permissionId) {
        roleDao.addPermissionToRole(roleId,permissionId);
    }

    /**
     * 删除角色拥有的权限
     */
    public void deletePermissionToRole(Long roleId, Long... permissionId) {
        roleDao.deletePermissionToRole(roleId,permissionId);
    }

}
