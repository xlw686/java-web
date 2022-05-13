package dao;

import com.kong.dao.DruidDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author kongkong
 * @date 20/11/30 15:27
 * @description:TODO
 * @since 1.8
 */
public class DruidQuery {
    public static void main(String[] args) throws SQLException {
        String sql="SELECT * FROM smbms_user where id=?;";
        ResultSet resultSet = null;
        Object[] objects={5};
        PreparedStatement st=null;
        ResultSet set = DruidDao.execute(DruidDao.getConnection(), st, resultSet, sql, objects);

        while (set.next()) {
            Object id = set.getObject("id");
            Object userCode = set.getObject("userCode");
            Object userName = set.getObject("userName");
            System.out.print("ID ：" + id + "  ");
            System.out.print("用户编号：" + userCode + "  ");
            System.out.print("用户姓名：" + userName + "  ");
            System.out.println(" ");
            System.out.println("Select Successful");

//            十一月 30, 2020 3:37:03 下午 com.alibaba.druid.pool.DruidDataSource info
//            信息: {dataSource-1} inited
//            ID ：5  用户编号：hanlubiao  用户姓名：韩路彪
//            Select Successful
        }
    }
}
