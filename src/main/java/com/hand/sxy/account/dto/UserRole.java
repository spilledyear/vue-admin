package com.hand.sxy.account.dto;


import java.util.Date;

public class UserRole {

    private Long userRoleId;

    private Long userId;

    private Long roleId;

    private String status;

    private Date caeateTime;

    private Date updateTime;


    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCaeateTime() {
        return caeateTime;
    }

    public void setCaeateTime(Date caeateTime) {
        this.caeateTime = caeateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}