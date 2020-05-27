package com.zhangzhigang.tm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangzhigang.tm.entity.Account;
import com.zhangzhigang.tm.utils.SSOClientUtil;

public class TMAuthInterceptor implements HandlerInterceptor {
	
	// 登录账号
	public static final String ACCOUNT = "username";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// 1. 判断本地是否存在会话 (isLogin = true)
		HttpSession session = request.getSession();
		Object account = session.getAttribute("account");
		if (null != account) {
			SSOClientUtil.appendLogOutUrl(request);
			return true;
		}
		
		// 2. 判断token
		String token = request.getParameter("token");
		if (!StringUtils.isEmpty(token)) {
			System.out.println("检测到服务器的token信息:" + token);
			// 验证Token
			String res = SSOClientUtil.authenticate(token, session.getId());
			if (!StringUtils.isEmpty(res)) {
				System.out.println("认证中心校验通过:" + res);
				JSONObject json = JSON.parseObject(res);
				account = new Account(json.getString(ACCOUNT));
				session.setAttribute("account", account);
				SSOClientUtil.appendLogOutUrl(request);
				return true;
			}
		}
		
		SSOClientUtil.redirectToSSOUrl(request, response);
		return false;
	}
	
}