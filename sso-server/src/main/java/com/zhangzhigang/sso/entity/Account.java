package com.zhangzhigang.sso.entity;

import java.io.Serializable;

/**
 * 账号信息
 * 
 * @author zhangzhigang
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 7587234996695487155L;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
