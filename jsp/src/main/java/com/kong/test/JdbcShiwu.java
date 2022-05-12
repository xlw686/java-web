package com.kong.test;

import java.sql.*;

//事务
public class JdbcShiwu {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url="jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSl=true";
        String username="root";
        String password="123456";

        Connection connection=null;
        Statement statement=null;

        try {
            //1.加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.存储连接数据库,代表数据库
            connection = DriverManager.getConnection(url, username, password);
            //3.像数据库发送SQL的对象Statement
            statement = connection.createStatement();

            //通知数据库开启事务，false 开启事务
            connection.setAutoCommit(false);

            //4.编写SQL
            String sql="update users set password=password-100 where name='空空';";
            String sql2="update users set password=password+100 where name='阿卡丽'";
            //5.执行sql
            int i = statement.executeUpdate(sql);
            int i1 = statement.executeUpdate(sql2);

            //提交事务
            connection.commit();
            System.out.println("成功");
        }catch (Exception e){
            connection.rollback();
            e.printStackTrace();
        }finally {
            //6.关闭连接，释放资源

            statement.close();
            connection.close();
        }




    }
}
