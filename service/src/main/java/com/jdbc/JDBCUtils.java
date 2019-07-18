package com.jdbc;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author: ZhouJi
 * @Description:
 * @Date: Created in 2019/7/11 15:56
 */
public class JDBCUtils {
    private static DataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtils.class.getResourceAsStream("/application-jdbc.properties");
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
