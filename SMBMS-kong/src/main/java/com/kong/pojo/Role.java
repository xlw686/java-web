package com.kong.pojo;

import java.util.Date;

/**
 * @author kongkong
 * @date 20/11/30 9:18
 * @description: ORM映射（表-->实体类）  对应数据库role表的实体类
 * @since 1.8
 */
public class Role {
    /**
     * id
     */
    private Integer id;
    //角色编码
    private String roleCode;
    //角色名称
    private String roleName;
    //创建者
    private Integer createdBy;
    //创建时间
    private Date creationDate;
    //更新者
    private Integer modifyBy;
    //更新时间
    private Date modifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
