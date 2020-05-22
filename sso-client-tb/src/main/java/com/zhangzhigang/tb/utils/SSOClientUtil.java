package com.zhangzhigang.tb.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhangzhigang.tb.config.SpringContextHolder;
import com.zhangzhigang.tb.properties.TBProperties;

public class SSOClientUtil {
	
	private static TBProperties tbProperties;
	
	/**
	 * 重定向到认证中心
	 */
	public static void redirectToSSOUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String redirectUrl = getRedirectUrl(request);
		StringBuilder url = new StringBuilder(getTBProperties().getSsoServerUrlPrefix())
				.append("/checkLogin?redirectUrl=")
				.append(redirectUrl);
		response.sendRedirect(url.toString());
	}
	
	
	/**
	 * 获取请求地址
	 */
	public static String getRedirectUrl(HttpServletRequest request) {
		return getTBProperties().getClientHostUrl() + request.getServletPath();
	}
	
	/**
	 * 获取客户端退出地址
	 */
	public static String getClientLogOutUrl() {
		return getTBProperties().getClientHostUrl() + "/logOut";
	}
	
	/**
	 * 获取认证中心退出地址
	 */
	public static String getSsoServerLogOutUrl() {
		return getTBProperties().getSsoServerUrlPrefix() + "/logOut";
	}
	
	public static TBProperties getTBProperties() {
		if (null == tbProperties) {
			tbProperties = SpringContextHolder.getBean(TBProperties.class);
		}
		return tbProperties;
	}
}