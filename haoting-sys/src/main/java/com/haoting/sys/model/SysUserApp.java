package com.haoting.sys.model;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午10:53 2017/8/21
 */
public class SysUserApp {

    private Long id;

    private Long userId;

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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
