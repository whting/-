package com.haoting.sys.dto;

import com.haoting.mvc.pagination.PageParam;

/**
 * @Author: smili
 */
public class SysUserSearchDTO extends PageParam {

    private String account;

    private Long appId;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
