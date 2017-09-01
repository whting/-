package com.haoting.sys.model;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午4:55 2017/8/21
 */
public class SysRolePermission {

    private Long id;

    private Long roleId;

    private Long permissionId;

    private Long appId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }


}
