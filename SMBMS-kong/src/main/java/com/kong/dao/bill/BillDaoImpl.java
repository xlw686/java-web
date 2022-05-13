package com.kong.dao.bill;

import com.mysql.cj.util.StringUtils;
import com.kong.dao.DruidDao;
import com.kong.pojo.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 订单管理Dao层
 * @date 20/12/13 8:26
 * @description: 订单管理的接口
 * @since 1.8
 */
public class BillDaoImpl implements BillDao {

    /**
     * @param connection
     * @param bill
     * @description: 添加订单的接口
     * @date 20/12/15 12:17
     * @Param: [connection, bill]
     * @Return: int
     */
    @Override
    public int addBill(Connection connection, Bill bill) throws Exception {
        PreparedStatement pstm = null;
        int addRows = 0;
        if (null != connection) {
            String sql = "insert into smbms_bill (billCode,productName,productDesc," +
                    "productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductDesc(),
                    bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                    bill.getProviderId(), bill.getCreatedBy(), bill.getCreationDate()};
            addRows = DruidDao.execute(connection, pstm, sql, params);
            DruidDao.closeResource(null, pstm, null);
        }
        return addRows;
    }

    /**
     * @param connection
     * @param billCode
     * @description: 根据billCode判断billCode是否存在
     * @date 20/12/15 12:19
     * @Param: [connection, billCode]
     * @Return: com.kongkong.pojo.Bill
     */
    @Override
    public Bill existBillCode(Connection connection, String billCode) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Bill bill = null;
        if (connection != null) {
            String sql = "select * from smbms_bill where billCode = ? ";
            Object[] params = {billCode};
            rs = DruidDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBillCode(rs.getString("billCode"));
                bill.setProductName(rs.getString("productName"));
                bill.setProductDesc(rs.getString("productDesc"));
                bill.setProductUnit(rs.getString("productUnit"));
                bill.setProductCount(rs.getBigDecimal("productCount"));
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                bill.setIsPayment(rs.getInt("isPayment"));
                bill.setCreationDate(rs.getTimestamp("creationDate"));
                bill.setCreatedBy(rs.getInt("createdBy"));
                bill.setProviderId(rs.getInt("providerId"));
            }
            DruidDao.closeResource(rs, pstm, null);
        }
        return bill;
    }

    /**
     * @param connection
     * @param delId
     * @description: 通过id删除订单
     * @date 20/12/15 12:21
     * @Param: [connection, delId]
     * @Return: int
     */
    @Override
    public int deleteBillById(Connection connection, Integer delId) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if (null != connection) {
            String sql = "delete from smbms_bill where id=?";
            Object[] params = {delId};
            flag = DruidDao.execute(connection, pstm, sql, params);
            DruidDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    /**
     * @param connection
     * @param bill
     * @description: 修改订单
     * @date 20/12/15 15:12
     * @Param: [connection, bill]
     * @Return: int
     */
    @Override
    public int billModify(Connection connection, Bill bill) throws Exception {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (null != connection) {
            String sql = "update smbms_bill set productName=?," +
                    "productDesc=?,productUnit=?,productCount=?,totalPrice=?," +
                    "isPayment=?,providerId=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {bill.getProductName(), bill.getProductDesc(),
                    bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                    bill.getProviderId(), bill.getModifyBy(), bill.getModifyDate(), bill.getId()};
            updateRows = DruidDao.execute(connection, pstm, sql, params);
            DruidDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * @param connection
     * @param id
     * @description: 通过ID获取一个订单信息
     * @date 20/12/15 15:13
     * @Param: [connection, id]
     * @Return: com.kongkong.pojo.Bill
     */
    @Override
    public Bill getBillById(Connection connection, String id) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Bill bill = null;
        if (connection != null) {
            String sql = "select b.*,p.proName as proName from smbms_bill b,smbms_provider p where b.providerId = p.id and b.id=?";
            Object[] params = {id};
            rs = DruidDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBillCode(rs.getString("billCode"));
                bill.setProductName(rs.getString("productName"));
                bill.setProductDesc(rs.getString("productDesc"));
                bill.setProductUnit(rs.getString("productUnit"));
                bill.setProductCount(rs.getBigDecimal("productCount"));
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                bill.setIsPayment(rs.getInt("isPayment"));
                bill.setCreationDate(rs.getTimestamp("creationDate"));
                bill.setCreatedBy(rs.getInt("createdBy"));
                bill.setProviderId(rs.getInt("providerId"));
                bill.setProviderName(rs.getString("proName"));
            }
            DruidDao.closeResource(rs, pstm, null);
        }

        return bill;
    }

    /**
     * @param connection
     * @param productName
     * @param providerId
     * @param isPayment
     * @description: 计算订单数
     * @date 20/12/15 15:23
     * @Param: [connection, productName, billCode, isPayment]
     * @Return: int
     */
    @Override
    public int getBillCount(Connection connection, String productName, int providerId, int isPayment) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_bill b,smbms_provider p where b.providerId=p.id");
            //定义一个列表，用于存放我们的参数
            ArrayList<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(productName)) {
                sql.append(" and b.productName like ?");
                list.add("%" + productName + "%");
            }
            if (providerId > 0) {
                sql.append(" and b.providerId = ?");
                list.add(providerId);
            }
            if (isPayment > 0) {
                sql.append(" and b.isPayment = ?");
                list.add(isPayment);
            }
            //把list转换成数组
            Object[] params = list.toArray();
            resultSet = DruidDao.execute(connection, pstm, resultSet, sql.toString(), params);
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            DruidDao.closeResource(resultSet, pstm, null);
        }
        return count;
    }

    /**
     * @param connection
     * @param productName
     * @param providerId
     * @param isPayment
     * @param currentPageNo
     * @param pageSize
     * @description: 查询订单列表
     * @date 20/12/15 15:24
     * @Param: [connection, productName, proCode, isPayment, currentPageNo, pageSize]
     * @Return: java.util.List<com.kongkong.pojo.Bill>
     */
    @Override
    public List<Bill> getBillList(Connection connection, String productName, int providerId, int isPayment, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Bill> billList = new ArrayList<Bill>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select b.*,p.proName as proName from smbms_bill b,smbms_provider p where b.providerId = p.id ");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(productName)) {
                sql.append(" and b.productName like ?");
                list.add("%" + productName + "%");
            }
            if (providerId > 0) {
                sql.append(" and b.providerId = ?");
                list.add(providerId);
            }
            if (isPayment > 0) {
                sql.append(" and b.isPayment = ?");
                list.add(isPayment);
            }
            //在数据库中，分页使用 limit startIndex，pagesize；总数
            //当前页（当前页-1）*页面大小
            //0，  5      1    0       01234
            //5，  5      2    5       26789
            //10， 5      3    10
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            Object[] params = list.toArray();
            rs = DruidDao.execute(connection, pstm, rs, sql.toString(), params);
            while (rs.next()) {
                Bill _bill = new Bill();
                _bill.setId(rs.getInt("id"));
                _bill.setBillCode(rs.getString("billCode"));
                _bill.setProductName(rs.getString("productName"));
                _bill.setProductDesc(rs.getString("productDesc"));
                _bill.setProductUnit(rs.getString("productUnit"));
                _bill.setProductCount(rs.getBigDecimal("productCount"));
                _bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                _bill.setIsPayment(rs.getInt("isPayment"));
                _bill.setCreationDate(rs.getTimestamp("creationDate"));
                _bill.setCreatedBy(rs.getInt("createdBy"));
                _bill.setProviderId(rs.getInt("providerId"));
                _bill.setProviderName(rs.getString("proName"));
                billList.add(_bill);
            }
            DruidDao.closeResource(rs, pstm, null);
        }
        return billList;
    }
}
