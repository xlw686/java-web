package com.kong.service.bill;

import com.kong.dao.DruidDao;
import com.kong.dao.bill.BillDao;
import com.kong.dao.bill.BillDaoImpl;
import com.kong.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Bill操作的Service层
 * @date 20/12/13 8:30
 * @description: 订单管理的服务层实现类
 * @since 1.8
 */
public class BillServiceImpl implements BillService {
    /**
     * 业务层都会调用dao层，所以我们要引入Dao层
     */
    private BillDao billDao;

    public BillServiceImpl() {
        billDao = new BillDaoImpl();
    }

    /**
     * @param bill
     * @description: 添加订单
     * @date 20/12/15 15:30
     * @Param: [bill]
     * @Return: boolean
     */
    @Override
    public boolean addBill(Bill bill) throws Exception {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = DruidDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            if (billDao.addBill(connection, bill) > 0) {
                flag = true;
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param delId
     * @description: 通过id删除订单
     * @date 20/12/15 15:31
     * @Param: [delId]
     * @Return: boolean
     */
    @Override
    public boolean deleteBillById(Integer delId) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            if (billDao.deleteBillById(connection, delId) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param bill
     * @description: 修改订单
     * @date 20/12/15 15:32
     * @Param: [bill]
     * @Return: boolean
     */
    @Override
    public boolean modifyBill(Bill bill) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            if (billDao.billModify(connection, bill) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param billCode
     * @description: 通过billCode查询订单，可以用于添加订单时判断billCode是否可用
     * @date 20/12/15 15:34
     * @Param: [billCode]
     * @Return: com.threepure.pojo.Bill
     */
    @Override
    public Bill billCodeExist(String billCode) {
        Connection connection = null;
        Bill bill = null;
        try {
            connection = DruidDao.getConnection();
            bill = billDao.existBillCode(connection, billCode);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return bill;
    }

    /**
     * @param id
     * @description: 通过id查询一订单信息
     * @date 20/12/15 15:36
     * @Param: [id]
     * @Return: com.threepure.pojo.Bill
     */
    @Override
    public Bill getBillById(String id) {
        Connection connection = null;
        Bill bill = null;
        try {
            connection = DruidDao.getConnection();
            bill = billDao.getBillById(connection, id);

        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return bill;
    }

    /**
     * @param productName
     * @param providerId
     * @param isPayment
     * @description: 获取订单个数
     * @date 20/12/15 15:37
     * @Param: [productName, billCode, isPayment]
     * @Return: int
     */
    @Override
    public int getBillCount(String productName, int providerId, int isPayment) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DruidDao.getConnection();
            count = billDao.getBillCount(connection, productName, providerId, isPayment);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return count;
    }

    /**
     * @param productName
     * @param providerId
     * @param isPayment
     * @param currentPageNo
     * @param pageSize
     * @description: 查询的订单列表
     * @date 20/12/15 15:39
     * @Param: [connection, productName, proCode, isPayment, currentPageNo, pageSize]
     * @Return: java.util.List<com.threepure.pojo.Bill>
     */
    @Override
    public List<Bill> getBillList(String productName, int providerId, int isPayment, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<Bill> billList = null;
        try {
            connection = DruidDao.getConnection();
            billList = billDao.getBillList(connection, productName, providerId, isPayment, currentPageNo, pageSize);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return billList;
    }
}
