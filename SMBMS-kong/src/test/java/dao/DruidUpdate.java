package dao;

import com.kong.dao.DruidDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author kongkong
 * @date 20/11/30 15:40
 * @description:TODO
 * @since 1.8
 */
public class DruidUpdate {
    public static void main(String[] args) throws SQLException {
//        String sql="UPDATE smbms_user SET `userPassword`=? WHERE id=?; ";
//        Object[] objects={5};
        String sql = "update smbms_user set userPassword = ? where id = ?";
        Object[] objects={1234123,1};
        PreparedStatement st=null;
        int i = DruidDao.execute(DruidDao.getConnection(), st, sql, objects);
        if (i>0){
            System.out.println("Updata Successful");

//            十一月 30, 2020 3:44:51 下午 com.alibaba.druid.pool.DruidDataSource info
//            信息: {dataSource-1} inited
//            Updata Successful

        }
    }
}
