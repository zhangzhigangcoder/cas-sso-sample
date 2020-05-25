package com.zhangzhigang.sso.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangzhigang.sso.config.SSOContext;
import com.zhangzhigang.sso.entity.ClientInfo;

@SuppressWarnings("unchecked")
@Controller
public class AuthController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@ResponseBody
	@GetMapping("/sessionId")
	public String getSessionId(HttpServletRequest request, HttpSession session) {
		String uid = null;
		if (request.getSession().getAttribute("uid") != null) {
			uid = request.getSession().getAttribute("uid").toString();
		}else {
			uid = UUID.randomUUID().toString();
		}
		request.getSession().setAttribute("uid", uid);
		System.out.println(session.getId());
		return request.getSession().getId();
	}
	
	@GetMapping
	public String index(HttpSession session, Model model) {
		String token = (String) session.getAttribute("token");
		if (StringUtils.isEmpty(token)) {
			// 未登录，跳转登录页面
			model.addAttribute("redirectUrl", "");
			return "login";
		} 
		return "success";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("redirectUrl","");
		return "login";
	}
	
	/**
	 * 登录
	 * @param model
	 * @param username
	 * @param password
	 * @param redirectUrl
	 * @param session
	 * @return
	 */
	@PostMapping("/login")
	public String doLogin(Model model, String username, String password, String redirectUrl, HttpSession session) {
		System.out.println(String.format("username=%s, password=%s", username, password));
		// 实际从DB判断
		String dbPass = SSOContext.ACCOUNT_MOCK.get(username);
		if (!StringUtils.isEmpty(dbPass) && dbPass.equals(password)) {
			// 登录成功
			// 1. 生成一个令牌
			String token = UUID.randomUUID().toString();
			System.out.println("生成token成功=>" +token);
			// 2. 记录登录token, 表明该账号登录过
			Map<String, String> account = new HashMap<String, String>();
			account.put(SSOContext.SSO_ACCOUNT, username);
			// 默认这里是永不过期的
			redisTemplate.opsForValue().set(SSOContext.SSO_PREFIX + token, account);
			// 3. 服务器中存储会话信息
			session.setAttribute("token", token);
			// 4. 返回给客户端
			if (StringUtils.isEmpty(redirectUrl)) {
				return "success";
			}
			return "redirect:" + redirectUrl+ "?token=" + token;
		}
		// 登录失败
		System.out.println("用户账号错误");
		model.addAttribute("error", "true");
		model.addAttribute("redirectUrl", redirectUrl);
		return "login";
	}
	
	/**
	 * 校验用户是否登录
	 * @param model
	 * @param redirectUrl
	 * @param session
	 * @return
	 */
	@GetMapping("/checkLogin")
	public String checkLogin(Model model, String redirectUrl, HttpSession session) {
		// 1. 判断是否登录
		String token = (String) session.getAttribute("token");
		if (StringUtils.isEmpty(token)) {
			// 未登录，跳转登录页面
			model.addAttribute("redirectUrl", redirectUrl);
			return "login";
		} 
		// 已登录，返回之前页面
		return "redirect:" + redirectUrl + "?token=" + token;
	}
	
	/**
	 * 验证token是否有效
	 * @param token
	 * @return
	 */
	@PostMapping("/auth")
	@ResponseBody
	public Map<String, String> verify(String token, String clientLogoutUrl, String jsessionId) {
		System.out.println("verify token: " + token);
		if (!StringUtils.isEmpty(token)) {
			Map<String, String> account = (HashMap<String, String>) redisTemplate.opsForValue().get(SSOContext.SSO_PREFIX + token);
			System.out.println(account);
			if (null != account) {
				Set<ClientInfo> clientList = SSOContext.CLIENT_LOGOUT_URLS.get(token);
				if (null == clientList) {
					clientList = new HashSet<>();
					SSOContext.CLIENT_LOGOUT_URLS.put(token, clientList);
				}
				clientList.add(new ClientInfo(clientLogoutUrl, jsessionId));
			}
			return account;
		}
		return null;
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpSession session, Model model, String redirectUrl) {
		session.invalidate();
		model.addAttribute("redirectUrl", redirectUrl);
		return "redirect:"+redirectUrl;
	}
	
}
