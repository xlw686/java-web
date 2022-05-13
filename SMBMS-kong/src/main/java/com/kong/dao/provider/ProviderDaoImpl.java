package com.kong.dao.provider;

import com.mysql.cj.util.StringUtils;
import com.kong.dao.DruidDao;
import com.kong.pojo.Bill;
import com.kong.pojo.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ThreePure
 * @date 20/12/13 8:25
 * @description: 供应商的数据库操作
 * @since 1.8
 */
public class ProviderDaoImpl implements ProviderDao{
    /**
     * @description: 添加供应商的接口
     * @param connection
     * @param provider
     * @date 20/12/13 8:40
     * @Param: [connection, provider]
     * @Return: int
     */
    @Override
    public int addProvider(Connection connection, Provider provider) throws Exception {
        PreparedStatement pstm=null;
        int addRows = 0;
        if (connection!=null){
            String sql = "insert into smbms_provider (proCode,proName,proDesc,proContact," +
                    "proPhone,proAddress,proFax,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?)";
            Object[] params = {provider.getProCode(),provider.getProName(),provider.getProDesc(),provider.getProContact(),
            provider.getProPhone(),provider.getProAddress(),provider.getProFax(),provider.getCreatedBy(),provider.getCreationDate()};
        addRows= DruidDao.execute(connection, pstm, sql, params);
        DruidDao.closeResource(null, pstm, null);
        }
        return addRows;
    }

    /**
     * @description: 判断供应商编码是否存在
     * @param connection
     * @param proCode
     * @date 20/12/15 8:56
     * @Param: [connection, provider]
     * @Return: com.threepure.pojo.Provider
     */
    @Override
    public Provider existProCode(Connection connection, String proCode) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Provider provider = null;
        if (connection!=null){
            String sql="select * from smbms_provider where proCode= ? ";
            Object[] params = {proCode};
            rs = DruidDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()){
                provider=new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreatedBy(rs.getInt("createdBy"));
                provider.setCreationDate(rs.getTimestamp("creationDate"));
                provider.setModifyBy(rs.getInt("modifyBy"));
                provider.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            DruidDao.closeResource(rs, pstm, null);
        }
        return provider;
    }

    /**
     * @description: 询目标供应商下的未支付订单数
     * @param connection
     * @param providerId
     * @date 20/12/15 11:02
     * @Param: [connection, providerId]
     * @Return: java.util.List<com.threepure.pojo.Bill>
     */
    @Override
    public List<Bill> noPayBill(Connection connection, int providerId) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Bill> billList=new ArrayList<Bill>();
        if (connection != null) {
            String sql="select * from smbms_bill where isPayment=1 and providerId=?;";
            Object[] params={providerId};
            rs=DruidDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                Bill _bill=new Bill();
                _bill.setId(rs.getInt("id"));
                _bill.setBillCode(rs.getString("billCode"));
                _bill.setProductName(rs.getString("productName"));
                _bill.setProductDesc(rs.getString("productDesc"));
                _bill.setProductUnit(rs.getString("productUnit"));
                _bill.setProductCount(rs.getBigDecimal("productCount"));
                _bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                _bill.setIsPayment(rs.getInt("isPayment"));
                _bill.setCreatedBy(rs.getInt("createdBy"));
                _bill.setCreationDate(rs.getTimestamp("creationDate"));
                _bill.setModifyBy(rs.getInt("modifyBy"));
                _bill.setModifyDate(rs.getTimestamp("modifyDate"));
                _bill.setProviderId(rs.getInt("providerId"));
                billList.add(_bill);
            }
            DruidDao.closeResource(rs, pstm, null);
        }
        return billList;
    }

    /**
     * @description: 删除供应商的接口
     * @param connection
     * @param delId
     * @date 20/12/13 8:41
     * @Param: [connection, delId]
     * @Return: int
     */
    @Override
    public int deleteProviderById(Connection connection, Integer delId) throws Exception {
        PreparedStatement pstm=null;
        int deleteRows=0;
        if(connection!=null){
            String sql="delete from smbms_provider where id=?;";
            Object[] params={delId};
            deleteRows = DruidDao.execute(connection, pstm, sql, params);
            DruidDao.closeResource(null, pstm, null);
        }
        return deleteRows;
    }

    /**
     * @description: 修改供应商的信息
     * @param connection
     * @param provider
     * @date 20/12/13 8:43
     * @Param: [connection, provider]
     * @Return: int
     */
    @Override
    public int providerModify(Connection connection, Provider provider) throws Exception {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (connection != null) {
            String sql="update smbms_provider set proName=?,proDesc=?,proContact=?,proPhone=?,proAddress=?,proFax=?," +
                    "modifyDate=?,modifyBy=? where  id=?;";
            Object[] params = {provider.getProName(),provider.getProDesc(),provider.getProContact(),provider.getProPhone(),
                    provider.getProAddress(),provider.getProFax(),provider.getModifyDate(),provider.getModifyBy(),provider.getId()};
            updateRows = DruidDao.execute(connection, pstm, sql, params);
            DruidDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * @description: 获取一个供应商信息
     * @param connection
     * @param id
     * @date 20/12/13 8:45
     * @Param: [connection, id]
     * @Return: com.threepure.pojo.Provider
     */
    @Override
    public Provider getProviderById(Connection connection, String id) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Provider provider = null;
        if (connection!=null){
            String sql="select * from smbms_provider where id= ? ";
            Object[] params = {id};
            rs = DruidDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()){
                provider=new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreatedBy(rs.getInt("createdBy"));
                provider.setCreationDate(rs.getTimestamp("creationDate"));
                provider.setModifyBy(rs.getInt("modifyBy"));
                provider.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            DruidDao.closeResource(rs, pstm, null);
        }
        return provider;
    }

    /**
     * @description: 获取供应商数量(根据供应商名或者供应商编码查询供应商总数)
     * @param connection
     * @param proName
     * @param proCode
     * @date 20/12/13 8:50
     * @Param: [connection, ProName, proCode]
     * @Return: int
     */
    @Override
    public int getProviderCount(Connection connection, String proName, String proCode) throws SQLException {

        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_provider where 1=1");
            //定义一个列表，用于存放我们的参数
            ArrayList<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(proName)) {
                sql.append(" and proName like ?");
                list.add("%" + proName + "%");
            }
            if (!StringUtils.isNullOrEmpty(proCode)) {
                sql.append(" and proCode like ? ;");
                list.add("%" +proCode+"%");
            }

            // 举个例子，这里拼接后的sql语句为：
            // select count(1) as count from smbms_provider where 1=1 and proCode like '%ZJ_GYS00%' ;
            // 发现like后面的模糊查询的字符串自动会被单引号''包住。这是正确的。
            // 如果拼接时：sql.append(" and proCode like ? ;");就会报空指针异常，参数超出。
            // 如果list.add("'%" +proCode+"%'");会被直接转译

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
     * @description: 获取供应商列表，满足模糊查询
     * @param connection
     * @param proName
     * @param proCode
     * @param currentPageNo
     * @param pageSize
     * @date 20/12/13 8:52
     * @Param: [connection, proName, proCode, currentPageNo, pageSize]
     * @Return: java.util.List<com.threepure.pojo.Provider>
     */
    @Override
    public List<Provider> getProviderList(Connection connection, String proName, String proCode, int currentPageNo, int pageSize) throws Exception {

        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Provider> providerList = new ArrayList<Provider>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_provider where 1=1");
            List<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(proName)) {

                sql.append(" and proName like ? ");
                list.add("%" + proName + "%");
            }

            if (!StringUtils.isNullOrEmpty(proCode)) {
                sql.append(" and proCode  like ? ");
                list.add("%"+proCode+"%");
            }
            if (pageSize!=0){
                sql.append(" order by creationDate DESC limit ?,? ");
                currentPageNo = (currentPageNo - 1) * pageSize;
                list.add(currentPageNo);
                list.add(pageSize);
            }

            Object[] params = list.toArray();

            rs = DruidDao.execute(connection, pstm, rs, sql.toString(), params);

            while (rs.next()) {
                Provider _provider = new Provider();
                _provider.setId(rs.getInt("id"));
                _provider.setProCode(rs.getString("proCode"));
                _provider.setProName(rs.getString("proName"));
                _provider.setProDesc(rs.getString("proDesc"));
                _provider.setProContact(rs.getString("proContact"));
                _provider.setProPhone(rs.getString("proPhone"));
                _provider.setProAddress(rs.getString("proAddress"));
                _provider.setProFax(rs.getString("proFax"));
                _provider.setCreationDate(rs.getTimestamp("creationDate"));
                providerList.add(_provider);
            }
            DruidDao.closeResource(rs, pstm, null);
        }
        return providerList;
    }

}
