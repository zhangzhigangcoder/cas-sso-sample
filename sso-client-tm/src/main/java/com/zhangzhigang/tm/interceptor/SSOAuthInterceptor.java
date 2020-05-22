package com.zhangzhigang.tm.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zhangzhigang.tm.config.SpringContextHolder;
import com.zhangzhigang.tm.properties.TMProperties;
import com.zhangzhigang.tm.utils.HttpClientUtil;
import com.zhangzhigang.tm.utils.SSOClientUtil;

public class SSOAuthInterceptor implements HandlerInterceptor {
	
	private TMProperties tbProperties;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 判断本地是否存在会话(isLogin=true)
		System.out.println(request.getRequestURI());
		HttpSession session = request.getSession();
		Boolean isLogin = (Boolean) session.getAttribute("isLogin");
		if (null != isLogin && isLogin) {
			return true;
		}
		// 2. 判断token
		String token = request.getParameter("token");
		if (!StringUtils.isEmpty(token)) {
			System.out.println("检测到服务器的token信息:" + token);
			// 防止伪造， 去服务器验证
			String url = getTBProperties().getSsoServerUrlPrefix() + "/verify";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("token", token);
			String result = HttpClientUtil.post(url, params);
			if (!StringUtils.isEmpty(result)) {
				System.out.println("认证中心校验通过:" + result);
				session.setAttribute("isLogin", true);
				return true;
			}
		}
		
		SSOClientUtil.redirectToSSOUrl(request, response);
		return false;
	}
	
	private TMProperties getTBProperties() {
		if (null == tbProperties) {
			tbProperties = SpringContextHolder.getBean(TMProperties.class);
		}
		return tbProperties;
	}
	
}