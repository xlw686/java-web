package com.kong.service.bill;

import com.kong.pojo.Bill;

import java.util.List;

/**
 * @author kongkong
 * @date 20/12/13 8:29
 * @description: 订单管理的服务层接口
 * @since 1.8
 */
public interface BillService {

    /**
     * @description: 添加订单
     * @date 20/12/15 15:30
     * @Param: [bill]
     * @Return: boolean
     */
    boolean addBill(Bill bill) throws Exception;

    /**
     * @description: 通过id删除订单
     * @date 20/12/15 15:31
     * @Param: [delId]
     * @Return: boolean
     */
    boolean deleteBillById(Integer delId);

    /**
     * @description: 修改订单
     * @date 20/12/15 15:32
     * @Param: [bill]
     * @Return: boolean
     */
    boolean modifyBill(Bill bill) throws Exception;

    /**
     * @description: 通过billCode查询订单，可以用于添加订单时判断billCode是否可用
     * @date 20/12/15 15:34
     * @Param: [billCode]
     * @Return: com.kongkong.pojo.Bill
     */
    Bill billCodeExist(String billCode) throws Exception;

    /**
     * @description: 通过id查询一订单信息
     * @date 20/12/15 15:36
     * @Param: [id]
     * @Return: com.kongkong.pojo.Bill
     */
    Bill getBillById(String id) throws Exception;

    /**
     * @description: 获取订单个数
     * @date 20/12/15 15:37
     * @Param: [productName, providerId, isPayment]
     * @Return: int
     */
    int getBillCount(String productName, int providerId, int isPayment) throws Exception;

    /**
     * @description: 查询的订单列表
     * @date 20/12/15 15:39
     * @Param: [productName, providerId, isPayment, currentPageNo, pageSize]
     * @Return: java.util.List<com.kongkong.pojo.Bill>
     */
    List<Bill> getBillList(String productName, int providerId, int isPayment, int currentPageNo, int pageSize) throws Exception;
}
