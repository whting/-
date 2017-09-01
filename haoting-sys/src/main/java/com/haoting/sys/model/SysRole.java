package com.haoting.sys.model;

import com.haoting.mvc.enums.TrueFalseEnum;

import java.util.Date;

/**
* @Author: smili
*/
public class SysRole{

    private Long id;

    private Long appId;

    private String name;

    private Boolean isEnable;

    private String description;

    private Integer sort;

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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Boolean getIsEnable(){
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable){
        this.isEnable = isEnable;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
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



    /** 以下为显示辅助参数 */
    private Boolean isChecked = Boolean.valueOf(false);

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getIsEnableStr() {
        return (isEnable != null && isEnable) ? TrueFalseEnum.TRUE.getLabel() : TrueFalseEnum.FALSE.getLabel();
    }
}