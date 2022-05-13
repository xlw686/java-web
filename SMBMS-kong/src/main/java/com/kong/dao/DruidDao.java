package com.kong.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author ThreePure
 * @date 20/11/30 9:25
 * @description: 数据库操作（持久层）
 * @since 1.8
 */
public class DruidDao {
    private static DataSource dataSource;

    //静态代码块，类加载时就完成了初始化
    static {
        try {
            InputStream in = DruidDao.class.getClassLoader().getResourceAsStream("druid.properties");
            Properties properties = new Properties();
            properties.load(in);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提供获取连接池的方法
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @description: 获取数据库连接的方法
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * @description:编写查询公共类
     */
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, String sql, Object[] params) throws SQLException {
        //预编译的sql，在后边直接执行就可以了
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject()占位符是从1开始，而数组是从0开始；
            preparedStatement.setObject(i + 1, params[i]);
        }
        System.out.println("DruidDao>>查>>SQL :" + preparedStatement.toString());
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    /**
     * @description:编写增删改公共方法
     */
    public static int execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject()占位符是从1开始，而数组是从0开始；
            preparedStatement.setObject(i + 1, params[i]);
        }
        System.out.println("DruidDao>>增删改>>SQL :" + preparedStatement.toString());
        return preparedStatement.executeUpdate();
    }

    /**
     * @description: 释放资源 --->使用DbUtils包下的DbUtils工具类提供的closeQuietly()方法
     */
    public static void closeResource(ResultSet resultSet, Statement statement, Connection connection) {
        DbUtils.closeQuietly(resultSet);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(connection);
    }
}
