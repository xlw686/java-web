package com.kong.service.user;

import com.kong.dao.DruidDao;
import com.kong.dao.user.UserDao;
import com.kong.dao.user.UserDaoImpl;
import com.kong.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kongkong
 * @date 20/12/1 20:12
 * @description: 判断用户登录
 * @since 1.8
 */
public class UserServiceImpl implements UserService {
    /**
     * 业务层都会调用dao层，所以我们要引入Dao层
     */
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }


    /**
     * @description: 实现用户登录
     * @date 22/5/10 8:36
     * @Param: [userCode, password]
     * @Return: com.kongkong.pojo.User
     */
    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
//            System.out.println("UserServiceImpl:"+userCode);
//            System.out.println("UserServiceImpl:"+password);
            //通过业务层调用对应的数据库操作
            connection = DruidDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        //进行密码匹配
        if (user != null) {
            if (!user.getUserPassword().equals(password)) {
                user = null;
            }
        }
        return user;
    }


    /**
     * @description: 实现用户修改密码
     * @date 22/5/10 8:38
     * @Param: [id, pwd]
     * @Return: int
     */
    @Override
    public boolean updatePwd(int id, String pwd) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = DruidDao.getConnection();
            if (userDao.updatePwd(connection, id, pwd) > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param userName
     * @param userRole
     * @description: 查询用户记录数
     * @date 22/5/10 17:12
     * @Param: [userName, userRole]
     * @Return: int
     */
    @Override
    public int getUserCount(String userName, int userRole) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DruidDao.getConnection();
            count = userDao.getUserCount(connection, userName, userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return count;
    }

    /**
     * @description: 根据条件查询用户列表
     * @date 22/5/10 17:58
     * @Param: [queryUserName, queryUserRole, currentPageNo, pageSize]
     * @Return: java.util.List<com.kongkong.pojo.User>
     */
    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;

        try {
            connection = DruidDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName, queryUserRole, currentPageNo, pageSize);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return userList;
    }

    /**
     * @param user
     * @description: 添加用户
     * @date 20/12/7 18:01
     * @Param: [user]
     * @Return: boolean
     */
    @Override
    public boolean add(User user) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            //关闭自动提交，默认开启jdbc事物
            connection.setAutoCommit(false);
            int addRows = userDao.add(connection, user);
            connection.commit();
            if (addRows > 0) {
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
     * @param userCode
     * @description: 根据UserCode查询用户
     * @date 20/12/7 18:03
     * @Param: [userCode]
     * @Return: com.kongkong.pojo.User
     */
    @Override
    public User selectUserCodeExist(String userCode) {
        Connection connection = null;
        User user = null;
        try {
            connection = DruidDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return user;
    }

    /**
     * @param delId
     * @description: 根据ID删除用户
     * @date 20/12/7 18:03
     * @Param: [delId]
     * @Return: boolean
     */
    @Override
    public boolean deleteUserById(Integer delId) throws Exception {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            int i = userDao.deleteUserById(connection, delId);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * @param id
     * @description: 根据id查找User
     * @date 20/12/7 18:04
     * @Param: [id]
     * @Return: com.kongkong.pojo.User
     */
    @Override
    public User getUserById(String id) {
        Connection connection = null;
        User user = null;
        try {
            connection = DruidDao.getConnection();
            user = userDao.getUserById(connection, id);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return user;
    }

    /**
     * @param user
     * @description: 修改用户信息
     * @date 20/12/7 18:05
     * @Param: [user]
     * @Return: boolean
     */
    @Override
    public boolean userModify(User user) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = DruidDao.getConnection();
            //关闭自动提交，默认开启jdbc事物
            connection.setAutoCommit(false);
            int updateRows = userDao.userModify(connection, user);
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


}
