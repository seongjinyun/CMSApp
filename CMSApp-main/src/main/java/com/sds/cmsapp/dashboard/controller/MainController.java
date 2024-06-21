package com.sds.cmsapp.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="dashboardMainController")
public class MainController {

	@GetMapping("/admin/dashboard")
	public String getMain() {
		return "admin/dashboard/activity";
	}
	
}