package com.haoting.sys.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.haoting.mvc.enums.TrueFalseEnum;
import com.haoting.mvc.model.PersistentObject;

import java.util.Date;

/**
 * 应用
 * 
 * @author Joe
 */
public class SysApp extends PersistentObject {

	private static final long serialVersionUID = 7902814112969375973L;
	
	/** 名称 */
	private String name;
	/** 编码  */
	private String code;
	/** 排序 */
	private Integer sort = Integer.valueOf(1);

	/** 是否启用 */
	private Boolean isEnable = Boolean.valueOf(true);

	/** 创建时间 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date gmtCreatedTime;
	/** 修改时间*/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date gmtModifiedTime;
	
	public SysApp(){
	}
	
	public SysApp(String name, String code, Integer sort, Boolean isEnable) {
		super();
		this.name = name;
		this.code = code;
		this.sort = sort;
		this.isEnable = isEnable;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Date getGmtCreatedTime() {
		return gmtCreatedTime;
	}

	public void setGmtCreatedTime(Date gmtCreatedTime) {
		this.gmtCreatedTime = gmtCreatedTime;
	}

	public Date getGmtModifiedTime() {
		return gmtModifiedTime;
	}

	public void setGmtModifiedTime(Date gmtModifiedTime) {
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
