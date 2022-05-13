package com.kong.dao.bill;

import com.kong.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kongkong
 * @date 20/12/13 8:26
 * @description: 订单的数据库操作接口
 * @since 1.8
 */
public interface BillDao {

    /**
     * @description: 添加订单的接口
     * @date 20/12/15 12:17
     * @Param: [connection, bill]
     * @Return: int
     */
    int addBill(Connection connection, Bill bill) throws Exception;

    /**
     * @description: 根据billCode判断billCode是否存在
     * @date 20/12/15 12:19
     * @Param: [connection, billCode]
     * @Return: com.kongkong.pojo.Bill
     */
    Bill existBillCode(Connection connection, String billCode) throws Exception;

    /**
     * @description: 通过id删除订单
     * @date 20/12/15 12:21
     * @Param: [connection, delId]
     * @Return: int
     */
    int deleteBillById(Connection connection, Integer delId) throws Exception;

    /**
     * @description: 修改订单
     * @date 20/12/15 15:12
     * @Param: [connection, bill]
     * @Return: int
     */
    int billModify(Connection connection, Bill bill) throws Exception;

    /**
     * @description: 通过获取一个订单信息
     * @date 20/12/15 15:13
     * @Param: [connection, id]
     * @Return: com.kongkong.pojo.Bill
     */
    Bill getBillById(Connection connection, String id) throws Exception;

    /**
     * @description: 计算订单数
     * @date 20/12/15 15:23
     * @Param: [connection, productName, providerId, isPayment]
     * @Return: int
     */
    int getBillCount(Connection connection, String productName, int providerId, int isPayment) throws SQLException;

    /**
     * @description: 查询订单列表
     * @date 20/12/15 15:24
     * @Param: [connection, productName, providerId, isPayment, currentPageNo, pageSize]
     * @Return: java.util.List<com.kongkong.pojo.Bill>
     */
    List<Bill> getBillList(Connection connection, String productName, int providerId, int isPayment, int currentPageNo, int pageSize) throws Exception;

}
