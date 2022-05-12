package com.kong.service.provider;

import com.kong.dao.DruidDao;
import com.kong.dao.provider.ProviderDao;
import com.kong.dao.provider.ProviderDaoImpl;
import com.kong.pojo.Bill;
import com.kong.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ThreePure
 * @date 20/12/13 8:29
 * @description: 供应商管理服务层实现类
 * @since 1.8
 */
public class ProviderServiceImpl implements ProviderService {

    /**
     * 业务层都会调用dao层，所以我们要引入Dao层
     */
    private ProviderDao providerDao;

    public ProviderServiceImpl() {
        providerDao = new ProviderDaoImpl();
    }

    /**
     * @param provider
     * @description: 添加供应商
     * @date 20/12/13 9:48
     * @Param: [provider]
     * @Return: boolean
     */
    @Override
    public boolean addProvider(Provider provider) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            //关闭自动提交，默认开启jdbc事物
            connection.setAutoCommit(false);
            int add = providerDao.addProvider(connection, provider);
            connection.commit();
            if (add > 0) {
                flag = true;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param providerId
     * @description: 判断未支付订单数
     * @date 20/12/15 11:05
     * @Param: [providerId]
     * @Return: int
     */
    @Override
    public int noPayBillCount(Integer providerId) {
        Connection connection = null;
        List<Bill> billList = null;
        int totalCount = 0;
        try {
            connection = DruidDao.getConnection();
            billList = providerDao.noPayBill(connection, providerId);
            totalCount = billList.size();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return totalCount;
    }

    /**
     * @param delId
     * @description: 根据id删除供应商
     * @date 20/12/13 9:49
     * @Param: [delId]
     * @Return: boolean
     */
    @Override
    public boolean deleteProviderById(Integer delId) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            int i = providerDao.deleteProviderById(connection, delId);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param provider
     * @description: 修改供应商
     * @date 20/12/13 9:49
     * @Param: [provider]
     * @Return: boolean
     */
    @Override
    public boolean providerModify(Provider provider) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            //关闭自动提交，默认开启jdbc事物
            connection.setAutoCommit(false);
            int updateRows = providerDao.providerModify(connection, provider);
            connection.commit();
            if (updateRows > 0) {
                flag = true;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param proCode
     * @description: 根据proCode查询供应商
     * @date 20/12/13 9:51
     * @Param: [proCode]
     * @Return: com.threepure.pojo.User
     */
    @Override
    public Provider providerCodeExist(String proCode) {
        Connection connection = null;
        Provider provider = null;
        try {
            connection = DruidDao.getConnection();
            provider = providerDao.existProCode(connection, proCode);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            provider = null;
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return provider;
    }

    /**
     * @param id
     * @description: 根据id获取一个供应商的信息
     * @date 20/12/13 9:51
     * @Param: [id]
     * @Return: com.threepure.pojo.Provider
     */
    @Override
    public Provider getProviderById(String id) {
        Connection connection = null;
        Provider provider = null;
        try {
            connection = DruidDao.getConnection();
            provider = providerDao.getProviderById(connection, id);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            provider = null;
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return provider;
    }

    /**
     * @param proName
     * @param proCode
     * @description: 获取供应商的总数
     * @date 20/12/13 9:53
     * @Param: [proName, proCode]
     * @Return: int
     */
    @Override
    public int getProviderCount(String proName, String proCode) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DruidDao.getConnection();
            count = providerDao.getProviderCount(connection, proName, proCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return count;
    }

    /**
     * @param queryProName
     * @param queryProCode
     * @param currentPageNo
     * @param pageSize
     * @description: 根据条件查询供应商列表
     * @date 20/12/13 9:55
     * @Param: [queryProName, queryProRole, currentPageNo, pageSize]
     * @Return: java.util.List<com.threepure.pojo.Provider>
     */
    @Override
    public List<Provider> getProviderList(String queryProName, String queryProCode, int currentPageNo, int pageSize) {

//        System.out.println("Service->queryProCode:"+queryProCode);
//        System.out.println("Service->queryProName:"+queryProName);
//        System.out.println("Service->currentPageNo:"+currentPageNo);
//        System.out.println("Service->pageSize:"+pageSize);

        Connection connection = null;
        List<Provider> providerList = null;
        try {
            connection = DruidDao.getConnection();
            providerList = providerDao.getProviderList(connection, queryProName, queryProCode, currentPageNo, pageSize);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return providerList;
    }
}
