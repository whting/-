package com.haoting.sys.model;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午9:58 2017/8/22
 */
public class SysUserRole {

    private Long id;

    private Long userId;

    private Long roleId;

    private Long appId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
