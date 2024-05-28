package com.sds.cmsapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {	
	@GetMapping("/settings/general")
	public String getGeneral() {
		return "settings/general";
	}
	
	@GetMapping("/settings/log")
	public String getLog() {
		return "settings/log";
	}
	
	@GetMapping("/settings/access")
	public String getAccess() {
		return "settings/access";
	}
	
	@GetMapping("/settings/mypage")
	public String getMypageInfo() {
		return "settings/mypage";
	}
	
	@GetMapping("/settings/user")
	public String getUserInfo() {
		return "settings/user";
	}
	
	@GetMapping("/settings/role")
	public String getRole() {
		return "settings/role";
	}
}
