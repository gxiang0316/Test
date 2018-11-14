package com.gordon.shiro.test03;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by gordon on 2018/9/11.
 */
public class JDBCTemplateUtils {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getInstance(){
        if(jdbcTemplate == null){
            synchronized (JDBCTemplateUtils.class){
                if(jdbcTemplate == null){
                    jdbcTemplate = createJdbcTemplate();
                }
            }
        }
        return jdbcTemplate;
    }

    private static JdbcTemplate createJdbcTemplate() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test_shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("123");
        return new JdbcTemplate(dataSource);
    }

}
