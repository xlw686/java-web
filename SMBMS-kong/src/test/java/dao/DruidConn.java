package dao;

import com.kong.dao.DruidDao;

import java.sql.SQLException;

/**
 * @author kongkong
 * @date 20/11/30 14:48
 * @description:TODO
 * @since 1.8
 */
public class DruidConn {
    public static void main(String[] args) {
        try {
            System.out.println(DruidDao.getConnection());

            /*十一月 30, 2020 2:51:37 下午 com.alibaba.druid.pool.DruidDataSource info
            信息: {dataSource-1} inited
            com.mysql.jdbc.JDBC4Connection@3ecf72fd*/

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
