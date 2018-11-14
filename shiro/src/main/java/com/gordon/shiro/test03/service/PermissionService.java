package com.gordon.shiro.test03.service;

import com.gordon.shiro.test03.dao.PermissionDaoImpl;
import com.gordon.shiro.test03.entity.Permission;

/**
 * Created by gordon on 2018/9/11.
 */
public class PermissionService {

    private PermissionDaoImpl permissionDao = new PermissionDaoImpl();

    /**
     * 添加权限
     */
    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    /**
     * 删除权限
     */
    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }

}
