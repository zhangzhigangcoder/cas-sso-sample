package com.zhangzhigang.sso.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings("unchecked")
@Controller
public class LoginController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public static final String SSO_PREFIX = "token:";
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
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
		if ("admin".equals(username) && "123456".equals(password)) {
			// 登录成功
			// 1. 生成一个令牌
			String token = UUID.randomUUID().toString();
			System.out.println("生成token成功=>" +token);
			// 2. 记录登录token, 表明该账号登录过
			Map<String, String> account = new HashMap<String, String>();
			account.put("username", username);
			redisTemplate.opsForValue().set(SSO_PREFIX + token, account, 60, TimeUnit.SECONDS);
			// 3. 服务器中存储会话信息
			session.setAttribute("token", token);
			// 4. 返回给客户端
			model.addAttribute("token", token);
			return "redirect:" + redirectUrl;
//			return "redirect:" + redirectUrl  + "?token=" + token;
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
		} else {
			// 已登录，返回之前页面
			model.addAttribute("token", token);
			return "redirect:" + redirectUrl;
		}
	}
	
	/**
	 * 验证token是否有效
	 * @param token
	 * @return
	 */
	@PostMapping("/verify")
	@ResponseBody
	public Map<String, String> verify(String token, HttpSession session) {
		System.out.println("verify token: " + token);
		if (!StringUtils.isEmpty(token)) {
			Map<String, String> account = (HashMap<String, String>) redisTemplate.opsForValue().get(SSO_PREFIX + token);
			System.out.println(account);
			if (null == account) {
				session.removeAttribute("token");
			}
			return account;
		}
		return null;
	}
	
}
