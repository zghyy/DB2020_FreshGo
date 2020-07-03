package xyz.zghy.freshgo.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;


public class DBUtil {
    private static final DBUtil dbUtil;

    static {
        dbUtil = new DBUtil();
    }

    private static ComboPooledDataSource dataSource;

    public DBUtil() {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("123abc456d");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/freshgo?useSSL=false");
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setInitialPoolSize(2);
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(10);
            dataSource.setMaxStatements(50);
            dataSource.setMaxIdleTime(60);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBUtil getInstance() {
        return dbUtil;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("无法连接数据库");
        }
    }
}