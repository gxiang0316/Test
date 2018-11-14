package com.gordon.shiro.test03.dao;

import com.gordon.shiro.test03.JDBCTemplateUtils;
import com.gordon.shiro.test03.entity.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by gordon on 2018/9/11.
 */
public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate = JDBCTemplateUtils.getInstance();

    @Override
    public Role createRole(Role role) {
        final String sql = "insert into sys_roles(role,description,available) values(?,?,?)";
        if(!checkRoleExists(role)) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws
                        SQLException {
                    PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                    psst.setString(1, role.getRole());
                    psst.setString(2, role.getDescription());
                    psst.setBoolean(3, role.getAvailable());
                    return psst;
                }
            }, keyHolder);
            role.setId(keyHolder.getKey().longValue());
        }
        return role;
    }

    private boolean checkRoleExists(Role role) {
        String sql = "select count(1) from sys_roles where role = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, role.getRole());
        return count != 0;
    }

    @Override
    public void deleteRole(Long roleId) {
        // 删除用户与该角色关联的数据
        String sql = "delete from sys_users_roles where role_id = ?";
        jdbcTemplate.update(sql,roleId);
        // 删除权限与该角色关联的数据
        sql = "delete from sys_roles_permissions where role_id = ?";
        jdbcTemplate.update(sql,roleId);
        // 删除该角色
        sql = "delete from sys_roles where id = ?";
        jdbcTemplate.update(sql,roleId);
    }

    @Override
    public void addPermissionToRole(Long roleId, Long... permissionId) {
        if(permissionId == null || permissionId.length == 0){
            return;
        }
        String sql = "insert into sys_roles_permissions (role_id,permission_id) values(?,?)";
        for (int i = 0;i < permissionId.length;i++){
            if(!exists(roleId,permissionId[i])) {
                jdbcTemplate.update(sql, roleId, permissionId[i]);
            }
        }
    }

    private boolean exists(Long roleId, Long permissionId) {
        String sql = "select count(1) from sys_roles_permissions where role_id = ? and permission_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roleId, permissionId);
        return count != 0;
    }

    @Override
    public void deletePermissionToRole(Long roleId, Long... permissionId) {
        String sql = "delete from sys_roles_permissions where role_id = ? and permission_id = ?";
        for (int i = 0 ; i < permissionId.length ; i++){
            jdbcTemplate.update(sql , roleId , permissionId[i]);
        }
    }
}
