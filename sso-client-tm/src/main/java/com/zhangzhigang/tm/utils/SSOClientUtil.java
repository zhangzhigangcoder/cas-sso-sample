package com.zhangzhigang.tm.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhangzhigang.tm.config.SpringContextHolder;
import com.zhangzhigang.tm.properties.TMProperties;

public class SSOClientUtil {
	
	private static TMProperties tbProperties;
	
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
	
	public static void appendLogOutUrl(HttpServletRequest request) {
		StringBuilder logoutUrl = new StringBuilder()
				.append(getClientLogOutUrl())
				.append("?redirectUrl=")
				.append(getRedirectUrl(request));
		request.setAttribute("logoutUrl", logoutUrl.toString());
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
	
	public static String getSsoServerAuthUrl() {
		return getTBProperties().getSsoServerUrlPrefix() + "/auth";
	}
	
	/**
	 * 获取客户端地址
	 */
	public static String getClientUrl() {
		return getTBProperties().getClientHostUrl();
	}
	
	public static TMProperties getTBProperties() {
		if (null == tbProperties) {
			tbProperties = SpringContextHolder.getBean(TMProperties.class);
		}
		return tbProperties;
	}
}