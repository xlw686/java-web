package com.kong.service.provider;

import com.kong.pojo.Provider;

import java.util.List;

/**
 * @author ThreePure
 * @date 20/12/13 8:28
 * @description: 供应商管理的服务层接口
 * @since 1.8
 */
public interface ProviderService {

    /**
     * @description: 添加供应商
     * @date 20/12/13 9:48
     * @Param: [provider]
     * @Return: boolean
     */
    boolean addProvider(Provider provider);

    /**
     * @description: 判断未支付订单数
     * @date 20/12/15 11:05
     * @Param: [providerId]
     * @Return: int
     */
    int noPayBillCount(Integer providerId);

    /**
     * @description: 根据id删除供应商
     * @date 20/12/13 9:49
     * @Param: [delId]
     * @Return: boolean
     */
    boolean deleteProviderById(Integer delId) throws Exception;

    /**
     * @description: 修改供应商
     * @date 20/12/13 9:49
     * @Param: [provider]
     * @Return: boolean
     */
    boolean providerModify(Provider provider);

    /**
     * @description: 根据proCode查询供应商
     * @date 20/12/13 9:51
     * @Param: [proCode]
     * @Return: com.threepure.pojo.User
     */
    Provider providerCodeExist(String proCode);

    /**
     * @description: 根据id获取一个供应商的信息
     * @date 20/12/13 9:51
     * @Param: [id]
     * @Return: com.threepure.pojo.Provider
     */
    Provider getProviderById(String id);

    /**
     * @description: 获取供应商的总数
     * @date 20/12/13 9:53
     * @Param: [proName, proCode]
     * @Return: int
     */
    int getProviderCount(String proName, String proCode);

    /**
     * @description: 根据条件查询供应商列表
     * @date 20/12/13 9:55
     * @Param: [queryProName, queryProRole, currentPageNo, pageSize]
     * @Return: java.util.List<com.threepure.pojo.Provider>
     */
    List<Provider> getProviderList(String queryProName, String queryProCode, int currentPageNo, int pageSize);

}
