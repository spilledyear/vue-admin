package com.hand.sxy.account.dto;

import java.util.Date;

public class RoleResource {

    private Long roleResourceId;

    private Long roleId;

    private Long resourceId;

    private String status;

    private Date caeateTime;

    private Date updateTime;

    public Long getRoleResourceId() {
        return roleResourceId;
    }

    public void setRoleResourceId(Long roleResourceId) {
        this.roleResourceId = roleResourceId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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
