package com.kong.test;

import java.sql.*;

public class TestJdbc {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url="jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSl=true";
        String username="root";
        String password="123456";

        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.存储连接数据库,代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);
        //3.像数据库发送SQL的对象Statement
        Statement statement = connection.createStatement();
        //4.编写SQL
        String sql="select * from users;";
        //5.执行sql
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            System.out.println("id="+rs.getObject("id"));
            System.out.println("name="+rs.getObject("name"));
            System.out.println("password="+rs.getObject("password"));
            System.out.println("birthday="+rs.getObject("birthday"));
        }

        //6.关闭连接，释放资源
        rs.close();
        statement.close();
        connection.close();

    }
}
