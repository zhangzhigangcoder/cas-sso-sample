package com.zhangzhigang.sso.config;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.zhangzhigang.sso.entity.ClientInfo;

public interface SSOContext {
	
	// 登录Token prefix
	public static final String SSO_PREFIX = "token:";
	
	// 客户端注销地址
	public static final Map<String, Set<ClientInfo>> CLIENT_LOGOUT_URLS = new ConcurrentHashMap<String, Set<ClientInfo>>();
}
