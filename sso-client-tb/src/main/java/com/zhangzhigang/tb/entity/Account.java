package com.zhangzhigang.tb.entity;

import java.io.Serializable;

/**
 * 账号信息
 * 
 * @author zhangzhigang
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 4844821494848556151L;
	
	private String username;
	
	public Account(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [username=");
		builder.append(username);
		builder.append("]");
		return builder.toString();
	}
	
}
