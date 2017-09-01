package com.haoting.sys.dto;

import com.haoting.mvc.pagination.PageParam;

/**
 * @Author: smili
 */
public class SysPermissionSearchDTO extends PageParam {


    private Long appId;

    private String name;

    private Boolean isEnable;


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean enable) {
        isEnable = enable;
    }
}
