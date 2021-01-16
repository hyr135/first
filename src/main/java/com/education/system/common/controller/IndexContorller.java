package com.education.system.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexContorller {
	//写方法接口
	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	//登录主页的显示信息
	@RequestMapping("/main")
	public String main() {
		return "main";
	}
	
}
