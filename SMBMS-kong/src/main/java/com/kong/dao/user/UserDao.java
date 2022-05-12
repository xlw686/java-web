package com.kong.dao.user;

import com.kong.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ThreePure
 * @date 20/12/1 19:47
 * @description: 用户接口
 * @since 1.8
 */
public interface UserDao {

    /**
     * @description: 得到登录的用户的接口
     * @date 20/12/1 19:49
     * @Param: [connection, userCode]
     * @Return:com.threepure.pojo.User
     */
    User getLoginUser(Connection connection, String userCode) throws SQLException;

    /**
     * @description: 修改密码的接口
     * @date 20/12/2 8:13
     * @Param: [connection, id, password]
     * @Return: int
     */
    int updatePwd(Connection connection, int id, String password) throws SQLException;

    /**
     * @description: 查询用户数量
     * @date 20/12/2 16:45
     * @Param: [connection, userName, userRole]
     * @Return: int
     */
    int getUserCount(Connection connection, String userName, int userRole) throws SQLException;

    /**
     * @description: 获取用户列表的接口
     * @date 20/12/2 17:32
     * @Param: [connection, userName, userRole, currentPageNo, pageSize]
     * @Return: java.util.List<com.threepure.pojo.User>
     */
    List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception;

    /**
     * @description: 添加用户
     * @date 20/12/7 17:22
     * @Param: [connection, user]
     * @Return: int
     */
    public int add(Connection connection, User user) throws Exception;

    /**
     * @description: 通过id删除用户
     * @date 20/12/7 17:22
     * @Param: [connection, delId]
     * @Return: int
     */
    public int deleteUserById(Connection connection, Integer delId) throws Exception;

    /**
     * @description: 通过id获取user
     * @date 20/12/7 17:23
     * @Param: [connection, id]
     * @Return: com.threepure.pojo.User
     */
    public User getUserById(Connection connection, String id) throws Exception;

    /**
     * @description: 修改用户信息
     * @date 20/12/7 17:27
     * @Param: [connection, user]
     * @Return: int
     */
    public int userModify(Connection connection, User user) throws Exception;

}
