package com.sds.cmssettings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String getMain() {
		return "settings/general";
	}
	
	@GetMapping("/general")
	public String getGeneral() {
		return "settings/general";
	}
	
	@GetMapping("/mypage")
	public String getMyInfo() {
		return "settings/mypage";
	}
	
	@GetMapping("/user")
	public String getUserInfo() {
		return "settings/user";
	}
	
	@GetMapping("/access")
	public String getAccess() {
		return "settings/access";
	}
	
	@GetMapping("/role")
	public String getRole() {
		return "settings/role";
	}
	
	@GetMapping("/log")
	public String getLog() {
		return "settings/log";
	}
}
