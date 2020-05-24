package com.zhangzhigang.tm.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zhangzhigang.tm.utils.HttpClientUtil;
import com.zhangzhigang.tm.utils.SSOClientUtil;

public class TMAuthInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 1. 判断本地是否存在会话 (isLogin = true)
		HttpSession session = request.getSession();
		Boolean isLogin = (Boolean) session.getAttribute("isLogin");
		if (null != isLogin && isLogin) {
			SSOClientUtil.appendLogOutUrl(request);
			return true;
		}
		// 2. 判断token
		String token = request.getParameter("token");
		if (!StringUtils.isEmpty(token)) {
			System.out.println("检测到服务器的token信息:" + token);
			// 防止伪造， 去服务器验证
			String url = SSOClientUtil.getSsoServerAuthUrl();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("token", token);
			params.put("jsessionId", session.getId());
			params.put("clientLogoutUrl", SSOClientUtil.getClientLogOutUrl());
			String result = HttpClientUtil.post(url, params);
			if (!StringUtils.isEmpty(result)) {
				System.out.println("认证中心校验通过:" + result);
				session.setAttribute("isLogin", true);
				SSOClientUtil.appendLogOutUrl(request);
				return true;
			}
		}
		
		SSOClientUtil.redirectToSSOUrl(request, response);
		return false;
	}
	
}