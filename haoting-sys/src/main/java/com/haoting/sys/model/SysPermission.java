package com.haoting.sys.model;

import java.util.Date;

/**
* @Author: smili
*/
public class SysPermission{

    private Long id;

    private Long appId;

    private Long parentId;

    private String icon;

    private String name;

    private String url;

    private Integer sort;

    private Boolean isEnable;

    private Boolean isMenu;

    private Date gmtCreatedTime;

    private Date gmtModifiedTime;


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getAppId(){
        return appId;
    }

    public void setAppId(Long appId){
        this.appId = appId;
    }

    public Long getParentId(){
        return parentId;
    }

    public void setParentId(Long parentId){
        this.parentId = parentId;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public Boolean getIsEnable(){
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable){
        this.isEnable = isEnable;
    }

    public Boolean getIsMenu(){
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu){
        this.isMenu = isMenu;
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

}