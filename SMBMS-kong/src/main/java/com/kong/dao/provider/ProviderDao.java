package com.kong.dao.provider;

import com.kong.pojo.Bill;
import com.kong.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kongkong
 * @date 20/12/13 8:24
 * @description: 供应商的数据库操作接口
 * @since 1.8
 */
public interface ProviderDao {

    /**
     * @description:添加供应商的接口
     * @date 20/12/13 8:40
     * @Param: [connection, provider]
     * @Return: int
     */
    public int addProvider(Connection connection, Provider provider) throws Exception;

    /**
     * @description: 判断供应商编码是否存在
     * @date 20/12/15 8:56
     * @Param: [connection, provider]
     * @Return: com.kongkong.pojo.Provider
     */
    public Provider existProCode(Connection connection, String proCode) throws Exception;

    /**
     * @description: 询目标供应商下的未支付订单数
     * @date 20/12/15 11:02
     * @Param: [connection, providerId]
     * @Return: java.util.List<com.kongkong.pojo.Bill>
     */
    List<Bill> noPayBill(Connection connection, int providerId) throws Exception;

    /**
     * @description:删除供应商的接口
     * @date 20/12/13 8:41
     * @Param: [connection, delId]
     * @Return: int
     */
    public int deleteProviderById(Connection connection, Integer delId) throws Exception;

    /**
     * @description:修改供应商的信息
     * @date 20/12/13 8:43
     * @Param: [connection, provider]
     * @Return: int
     */
    public int providerModify(Connection connection, Provider provider) throws Exception;

    /**
     * @description:获取一个供应商信息
     * @date 20/12/13 8:45
     * @Param: [connection, id]
     * @Return: com.kongkong.pojo.Provider
     */
    public Provider getProviderById(Connection connection, String id) throws Exception;

    /**
     * @description:获取供应商数量
     * @date 20/12/13 8:50
     * @Param: [connection, ProName, proCode]
     * @Return: int
     */
    int getProviderCount(Connection connection, String proName, String proCode) throws SQLException;

    /**
     * @description:获取供应商列表，满足模糊查询
     * @date 20/12/13 8:52
     * @Param: [connection, proName, proCode, currentPageNo, pageSize]
     * @Return: java.util.List<com.kongkong.pojo.Provider>
     */
    List<Provider> getProviderList(Connection connection, String proName, String proCode, int currentPageNo, int pageSize) throws Exception;


}
