package com.kong.dao.role;

import com.kong.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ThreePure
 * @date 20/12/2 19:18
 * @description:TODO
 * @since 1.8
 */
public interface RoleDao {
    /**
     * @date 20/12/2 19:04
     * @description: 获取角色列表
     * @Param: [connection]
     * @Return: java.util.List<com.threepure.pojo.Role>
     */
    List<Role> getRoleList(Connection connection) throws SQLException;
}
