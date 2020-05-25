package com.zhangzhigang.sso.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.zhangzhigang.sso.entity.ClientInfo;

public interface SSOContext {
	
	// 登录Token prefix
	public static final String SSO_PREFIX = "token:";
	
	// 登录账号
	public static final String SSO_ACCOUNT = "username";
	
	// 客户端注销地址
	public static final Map<String, Set<ClientInfo>> CLIENT_LOGOUT_URLS = new ConcurrentHashMap<String, Set<ClientInfo>>();
	
	// 登录账号
	public static final Map<String, String> ACCOUNT_MOCK = new HashMap<String, String>(){
		{
			put("test", "123456");
			put("admin", "123456");
		}
	};
}
