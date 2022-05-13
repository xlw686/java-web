package com.kong.pojo;

import java.util.Date;

/**
 * @author kongkong
 * @date 20/11/30 9:12
 * @description: ORM映射（表-->实体类）  对应数据库user表的实体类
 * @since 1.8
 */
public class User {
    //静态属性
    /*id*/
    private Integer id;
    //用户编码
    private String userCode;
    //用户名称
    private String userName;
    //用户密码
    private String userPassword;
    //性别
    private Integer gender;
    //出生日期
    private Date birthday;
    //电话
    private String phone;
    //地址
    private String address;
    //用户角色
    private Integer userRole;
    //创建者
    private Integer createdBy;
    //创建时间
    private Date creationDate;
    //更新者
    private Integer modifyBy;
    //更新时间
    private Date modifyDate;

    //年龄
    private Integer age;

    //用户角色名称
    private String userRoleName;

    //getter和setter方法

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
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

    public Integer getAge() {
        Date date = new Date();
        Integer age = date.getYear() - birthday.getYear();
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }
}
