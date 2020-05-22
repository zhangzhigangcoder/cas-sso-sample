package com.zhangzhigang.tb.entity;

/**
 * 账号信息
 * 
 * @author zhangzhigang
 */
public class Account {

	private String username;

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
