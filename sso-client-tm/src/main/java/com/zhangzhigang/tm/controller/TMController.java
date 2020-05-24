package com.zhangzhigang.tm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TMController {
	
	@GetMapping
	public String index(Model model) {
		return "index";
	}
	
	@PostMapping("/logOut")
	@ResponseBody
	public void logOut(HttpSession session) {
		session.invalidate();
		System.out.println("tm清除session");
	}
	
}
