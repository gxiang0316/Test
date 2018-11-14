package com.gordon.jasper.JDBCUtil;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by gordon on 2018/8/29.
 */
public class JDBCUtil {

    private static String driverClass;
    private static String url;
    private static String user;
    private static String password;

    static{
        try {
            ClassLoader loader = JDBCUtil.class.getClassLoader();
            InputStream resourceAsStream = loader.getResourceAsStream("jdbc.properties");
            Properties props = new Properties();
            props.load(resourceAsStream);
            driverClass = props.getProperty("jdbc.driverClassName");
            url = props.getProperty("jdbc.url");
            user = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");

            Class.forName(driverClass);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    public static void release(ResultSet rs, Statement stmt, Connection conn){
        try {
            if(rs != null){
                rs.close();
                rs = null;
            }
            if(stmt != null){
                stmt.close();
                stmt = null;
            }
            if (conn != null){
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
