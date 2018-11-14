package com.gordon.shiro.test03.dao;

import com.gordon.shiro.test03.entity.Permission;

/**
 * Created by gordon on 2018/9/11.
 */
public interface PermissonDao {
    /** 添加权限 */
    Permission createPermission(Permission permission);
    /** 删除权限 */
    void deletePermission(Long permissionId);

}
