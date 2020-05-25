package com.zhangzhigang.tb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangzhigang.tb.utils.SSOClientUtil;


@Controller
public class TBController {
	
	@GetMapping
	public String index() {
		return "index";
	}
	
	@PostMapping("/logOut")
	@ResponseBody
	public void logOut(HttpSession session) {
		session.invalidate();
		System.out.println("tb清除session:" + session.getId());
	}
	
	@GetMapping("/logOut")
	public String localLogOut(HttpSession session, HttpServletRequest request) {
		session.invalidate();
		System.out.println("tb清除session:" + session.getId());
		return "redirect:" + SSOClientUtil.getSsoServerLogOutUrl() + "?redirectUrl=" + request.getParameter("redirectUrl");
	}
}
