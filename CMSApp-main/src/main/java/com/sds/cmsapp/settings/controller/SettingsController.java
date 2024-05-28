package com.sds.cmsapp.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.cmsapp.domain.Owner;
import com.sds.cmsapp.model.owner.OwnerService;

@Controller
public class SettingsController {	
	
	@Autowired
	private OwnerService ownerService;
	
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
	
	@PostMapping("/settings/owner/update")
	public String regist(Owner owner) {	
		ownerService.update(owner);
		return "redirect:/settings/general";
	}
	
}
