package com.haoting.sys.dto;

import com.haoting.mvc.pagination.PageParam;

/**
 * @Author: smili
 */
public class SysRoleSearchDTO extends PageParam {

    private String name;

    private String appId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
