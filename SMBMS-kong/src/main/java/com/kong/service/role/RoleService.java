package com.kong.service.role;

import com.kong.pojo.Role;

import java.util.List;

/**
 * @author ThreePure
 * @date 20/12/2 19:19
 * @description: 用户角色接口
 * @since 1.8
 */
public interface RoleService {

    /**
     * @date 20/12/2 19:24
     * @description: 获取角色列表的抽象方法
     * @Param: []
     * @Return: java.util.List<com.threepure.pojo.Role>
     */
    List<Role> getRoleList();
}
