package com.gordon.shiro.test03.dao;

import com.gordon.shiro.test03.JDBCTemplateUtils;
import com.gordon.shiro.test03.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gordon on 2018/9/11.
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = JDBCTemplateUtils.getInstance();

    @Override
    public User createUser(User user) {
        if(!checkUserExists(user)){
            String sql = "insert into sys_users (username,password,salt,locked) values (?,?,?,?)";
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws
                        SQLException {
                    PreparedStatement psst = connection.prepareStatement(sql,new String[]{"id"});
                    psst.setString(1,user.getUsername());
                    psst.setString(2,user.getPassword());
                    psst.setString(3,user.getSalt());
                    psst.setBoolean(4,user.getLocked());
                    return psst;
                }
            },keyHolder);
            user.setId(keyHolder.getKey().longValue());
        }
        return user;
    }

    private boolean checkUserExists(User user) {
        String sql = "select count(1) from sys_users where username = ? and password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user.getUsername(),
                user.getPassword());
        return count != 0;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update sys_users set username = ? , password = ? , salt = ? , locked = ? where id = ?";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getSalt(),user.getLocked(),user.getId());
    }

    @Override
    public void deleteUser(User user) {
        String sql = "delete from sys_users where id = ?";
        jdbcTemplate.update(sql,user.getId());
    }

    @Override
    public void addRoleToUser(Long userId, Long... roleId) {
        String sql = "insert into sys_users_roles (user_id,role_id) values(?,?)";
        for(Long roleid : roleId){
            jdbcTemplate.update(sql,userId,roleid);
        }
    }

    @Override
    public void deleteRoleToUser(Long userId, Long... roleId) {
        String sql = "delete from sys_users_roles where user_id = ? and role_id = ?";
        for (Long roleid : roleId){
            jdbcTemplate.update(sql,userId,roleid);
        }
    }

    @Override
    public User findUserById(Long userId) {
        String sql = "select id,username,password,salt,locked from sys_users where id = ?";
        User user = (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), userId);
        return user;
    }

    @Override
    public User findUserByName(String username) {
        String sql = "select id,username,password,salt,locked from sys_users where username = ?";
        User user = (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), username);
        return user;
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        String sql = "select c.role from sys_users_roles a , sys_users b , sys_roles c " +
                "where b.username = ? and a.user_id = b.id and a.role_id = c.id";
        Set userRoleList = new HashSet(jdbcTemplate.queryForList(sql, String.class, username));

        return userRoleList;
    }

    @Override
    public Set<String> findPermissionByUsername(String username) {
        String sql = "select a.permission from sys_roles_permissions a , sys_users_roles b , sys_roles c ,sys_users d" +
                "where d.username = ? and d.id = b.user_id and b.role_id = c.id and c.id = a.role_id";

        Set userPermissionList = new HashSet(jdbcTemplate.queryForList(sql, String.class, username));

        return userPermissionList;
    }
}
