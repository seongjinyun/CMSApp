package com.sds.cmsdocument.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String getMain() {
		// return "main/index";

		// 로그인 페이지 호출
		return "/login/loginForm";
	}
	

	
	
}
