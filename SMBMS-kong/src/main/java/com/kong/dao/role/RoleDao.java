package com.kong.dao.role;

import com.kong.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kongkong
 * @date 22/5/10 19:18
 * @description:TODO
 * @since 1.8
 */
public interface RoleDao {
    /**
     * @date 22/5/10 19:04
     * @description: 获取角色列表
     * @Param: [connection]
     * @Return: java.util.List<com.kongkong.pojo.Role>
     */
    List<Role> getRoleList(Connection connection) throws SQLException;
}
