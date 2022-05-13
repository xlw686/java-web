package com.kong.service.role;

import com.kong.dao.DruidDao;
import com.kong.dao.role.RoleDao;
import com.kong.dao.role.RoleDaoImpl;
import com.kong.pojo.Role;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kongkong
 * @date 22/5/10 19:19
 * @description:TODO
 * @since 1.8
 */
public class RoleServiceImpl implements RoleService {
    //获得一个RoleDao对象
    private RoleDao roleDao;

    public RoleServiceImpl() {
        this.roleDao = new RoleDaoImpl();
    }

    /**
     * @date 22/5/10 19:24
     * @description: 获取角色列表的抽象方法
     * @Return: java.util.List<com.kongkong.pojo.Role>
     */
    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = null;
        Connection connection = null;
        try {
            connection = DruidDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidDao.closeResource(null, null, connection);
        }
        return roleList;
    }


    @Test
    public void test() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }
}
