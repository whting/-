package com.haoting.client;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 已登录用户信息
 * 
 * @author Joe
 */
public class SessionUser implements Serializable {

	private static final long serialVersionUID = 1764365572138947234L;

	// 登录用户访问Token
	private String token;

	private Long userId;

	private String account;

	private String password;

	private Boolean isEnable;

	private String lastLoginIp;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;


	public SessionUser() {
		super();
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return isEnable;
	}

	public void setEnable(Boolean enable) {
		isEnable = enable;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
