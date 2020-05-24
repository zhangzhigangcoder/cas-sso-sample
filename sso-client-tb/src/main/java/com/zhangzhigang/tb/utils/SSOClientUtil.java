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
		StringBuilder url = new StringBuilder()
				.append(getTBProperties().getSsoServerUrlPrefix())
				.append("/checkLogin?redirectUrl=")
				.append(getRedirectUrl(request));
		response.sendRedirect(url.toString());
	}
	
	public static void appendLogOutUrl(HttpServletRequest request) {
		StringBuilder logoutUrl = new StringBuilder()
				.append(getSsoServerLogOutUrl())
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
	
	public static TBProperties getTBProperties() {
		if (null == tbProperties) {
			tbProperties = SpringContextHolder.getBean(TBProperties.class);
		}
		return tbProperties;
	}
}