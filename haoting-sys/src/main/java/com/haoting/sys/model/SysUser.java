package com.haoting.sys.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.haoting.mvc.enums.TrueFalseEnum;

import java.util.Date;

/**
* @Author: smili
*/
public class SysUser{

    private Long id;

    private String account;

    private String password;

    private Boolean isEnable;

    private String lastLoginIp;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreatedTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModifiedTime;


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getAccount(){
        return account;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Boolean getIsEnable(){
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable){
        this.isEnable = isEnable;
    }

    public String getLastLoginIp(){
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp){
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime(){
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public Date getGmtCreatedTime(){
        return gmtCreatedTime;
    }

    public void setGmtCreatedTime(Date gmtCreatedTime){
        this.gmtCreatedTime = gmtCreatedTime;
    }

    public Date getGmtModifiedTime(){
        return gmtModifiedTime;
    }

    public void setGmtModifiedTime(Date gmtModifiedTime){
        this.gmtModifiedTime = gmtModifiedTime;
    }


    public String getIsEnableStr() {
        return (isEnable != null && isEnable) ? TrueFalseEnum.TRUE.getLabel() : TrueFalseEnum.FALSE.getLabel();
    }

}