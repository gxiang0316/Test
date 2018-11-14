package com.gordon.shiro.test03.dao;

import com.gordon.shiro.test03.JDBCTemplateUtils;
import com.gordon.shiro.test03.entity.Permission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by gordon on 2018/9/11.
 */
public class PermissionDaoImpl implements PermissonDao {

    private JdbcTemplate jdbcTemplate = JDBCTemplateUtils.getInstance();

    @Override
    public Permission createPermission(Permission permission) {
        final String sql = "insert into sys_permissions(permission,description,available) value (?,?,?)";
        if(!checkPermissionExists(permission)) {
            // Spring利用GeneratedKeyHolder，提供了一个可以返回新增记录对应主键值的方法：
            // int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder)
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws
                        SQLException {
                    PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                    psst.setString(1, permission.getPromission());
                    psst.setString(2, permission.getDescription());
                    psst.setBoolean(3, permission.getAvailable());
                    return psst;
                }
            }, keyHolder);

            permission.setId(keyHolder.getKey().longValue());
        }
        return permission;
    }

    private boolean checkPermissionExists(Permission permission) {
        String sql = "select count(1) from sys_permissions where permission = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, permission
                .getPromission());
        return count != 0;
    }

    @Override
    public void deletePermission(Long permissionId) {
        // 先删除关联表中数据
        String sql = "delete from sys_roles_permissions where permission_id = ?";
        jdbcTemplate.update(sql,permissionId);

        sql = "delete from sys_permissions where permission_id = ?";
        jdbcTemplate.update(sql,permissionId);
    }
}
