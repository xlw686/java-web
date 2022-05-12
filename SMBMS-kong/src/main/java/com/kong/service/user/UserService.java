package com.kong.service.user;

import com.kong.pojo.User;

import java.util.List;

/**
 * @author ThreePure
 * @date 20/12/1 20:11
 * @description: 用户接口
 * @since 1.8
 */
public interface UserService {

    /**
     * @description: 用户登录
     * @date 20/12/1 20:11
     * @Param: [userCode, password]
     * @Return: com.threepure.pojo.User
     */
    User login(String userCode, String password);

    /**
     * @description: 修改用户密码的业务层
     * @date 20/12/2 8:34
     * @Param: [id, pwd]
     * @Return: int
     */
    boolean updatePwd(int id, String pwd);

    /**
     * @description: 查询用户记录数
     * @date 20/12/2 17:12
     * @Param: [userName, userRole]
     * @Return: int
     */
    int getUserCount(String userName, int userRole);

    /**
     * @description: 根据条件查询用户列表
     * @date 20/12/2 17:58
     * @Param: [queryUserName, queryUserRole, currentPageNo, pageSize]
     * @Return: java.util.List<com.threepure.pojo.User>
     */
    List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

    /**
     * @description: 添加用户
     * @date 20/12/7 18:01
     * @Param: [user]
     * @Return: boolean
     */
    boolean add(User user);

    /**
     * @description: 根据UserCode查询用户
     * @date 20/12/7 18:03
     * @Param: [userCode]
     * @Return: com.threepure.pojo.User
     */
    User selectUserCodeExist(String userCode);

    /**
     * @description: 根据ID删除用户
     * @date 20/12/7 18:03
     * @Param: [delId]
     * @Return: boolean
     */
    boolean deleteUserById(Integer delId) throws Exception;

    /**
     * @description: 根据id查找User
     * @date 20/12/7 18:04
     * @Param: [id]
     * @Return: com.threepure.pojo.User
     */
    User getUserById(String id);

    /**
     * @description: 修改用户信息
     * @date 20/12/7 18:05
     * @Param: [user]
     * @Return: boolean
     */
    boolean userModify(User user);


}
